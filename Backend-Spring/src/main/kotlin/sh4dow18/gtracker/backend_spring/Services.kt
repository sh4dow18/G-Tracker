package sh4dow18.gtracker.backend_spring

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.FileCopyUtils
import org.springframework.web.multipart.MultipartFile
import java.awt.geom.Ellipse2D
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.net.URL
import java.nio.file.Paths
import java.time.ZonedDateTime
import javax.imageio.ImageIO

@Bean
fun passwordEncoder(): BCryptPasswordEncoder {
    return BCryptPasswordEncoder()
}

interface UserService {
    fun findUserById(email: String): UserResponse
    fun userRegistration(userRegistrationRequest: UserRegistrationRequest): UserResponse
    fun updateUser(updateUserRequest: UpdateUserRequest, image: MultipartFile?): UserResponse
    fun closeAccount(email: String): UserResponse
    fun serveProfileImage(imageName: String?, response: HttpServletResponse)
}
@Service
class AbstractUserService(
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val userMapper: UserMapper,
    @Autowired
    val mappingService: MappingService,
    @Value("\${upload.path}")
    val uploadPath: String,
    @Autowired
    val logService: LogService
): UserService {
    @Throws(NoSuchElementException::class)
    override fun findUserById(email: String): UserResponse {
        val user: User = userRepository.findById(email).orElseThrow {
            NoSuchElementException(String.format("The User with the email %s does not exists", email))
        }
        return userMapper.userToUserResponse(user, mappingService)
    }
    @Transactional(rollbackFor = [ElementAlreadyExists::class])
    @Throws(ElementAlreadyExists::class)
    override fun userRegistration(userRegistrationRequest: UserRegistrationRequest): UserResponse {
        val user: User? = userRepository.findById(userRegistrationRequest.email).orElse(null)
        if (user != null) {
            throw ElementAlreadyExists(String.format("The User with the email %s already exists", userRegistrationRequest.email))
        }
        val newUser = userMapper.userRegistrationRequestToUser(userRegistrationRequest, mappingService, passwordEncoder())
        val response = userMapper.userToUserResponse(userRepository.save(newUser), mappingService)
        logService.registerLog(
            RegisterLogRequest(
                action = "New User",
                actionType = 1,
                user = response.email
            )
        )
        return response
    }
    @Transactional(rollbackFor = [NoSuchElementException::class, BadRequestException::class, ElementAlreadyExists::class])
    @Throws(NoSuchElementException::class, BadRequestException::class, ElementAlreadyExists::class)
    override fun updateUser(updateUserRequest: UpdateUserRequest, image: MultipartFile?): UserResponse {
        val user: User = userRepository.findById(updateUserRequest.email).orElseThrow {
            NoSuchElementException(String.format("The User with the email %s does not exists", updateUserRequest.email))
        }
        val anotherUser: User? = userRepository.findByUserName(updateUserRequest.userName).orElse(null)
        if (anotherUser != null) {
            throw ElementAlreadyExists(String.format("Already exists the user %s", updateUserRequest.userName))
        }
        if (updateUserRequest.userName != "") {
            userMapper.updateUser(updateUserRequest, user)
        }
        if (image != null) {
            // Verifies if the image file is really an Image
            if (ImageIO.read(image.inputStream) == null) {
                throw BadRequestException("Image file type not supported")
            }
            // Create a New Image 200 x 200 from the original image
            val originalImage = ImageIO.read(image.inputStream)
            val targetWidth = 150
            val targetHeight = 150
            val xScale = targetWidth.toDouble() / originalImage.width
            val yScale = targetHeight.toDouble() / originalImage.height
            val scale = if (xScale > yScale) xScale else yScale
            val newWidth = (originalImage.width * scale).toInt()
            val newHeight = (originalImage.height * scale).toInt()
            val resizedImage = BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB)
            val graphics = resizedImage.createGraphics()
            graphics.clip = Ellipse2D.Float(0f, 0f, targetWidth.toFloat(), targetHeight.toFloat())
            graphics.drawImage(originalImage, (targetWidth - newWidth) / 2, (targetHeight - newHeight) / 2, newWidth, newHeight, null)
            graphics.dispose()
            // Add the "User" email plus ".jpg" has the image path
            val uniqueFileName = updateUserRequest.email + ".png"
            // Renders the image as "jpg" and save it in the path "uploadPath"
            ImageIO.write(resizedImage, "png", File("$uploadPath/users/$uniqueFileName"))
            user.image = true
        }
        val response = userMapper.userToUserResponse(userRepository.save(user), mappingService)
        logService.registerLog(
            RegisterLogRequest(
                action = "Update Profile",
                actionType = 2,
                user = response.email
            )
        )
        return response
    }
    @Transactional(rollbackFor = [NoSuchElementException::class])
    @Throws(NoSuchElementException::class)
    override fun closeAccount(email: String): UserResponse {
        val user: User = userRepository.findById(email).orElseThrow {
            NoSuchElementException(String.format("The User with the email %s does not exists", email))
        }
        user.password = null
        user.enabled = false
        val response = userMapper.userToUserResponse(userRepository.save(user), mappingService)
        logService.registerLog(
            RegisterLogRequest(
                action = "Close Account",
                actionType = 2,
                user = response.email
            )
        )
        return response
    }
    @Transactional(rollbackFor = [IOException::class, NoSuchElementException::class])
    @Throws(IOException::class, NoSuchElementException::class)
    override fun serveProfileImage(imageName: String?, response: HttpServletResponse) {
        try {
            val imagePath = Paths.get("$uploadPath/users/", imageName).toAbsolutePath().toString()
            response.contentType = "image/png"
            FileInputStream(imagePath).use { imageStream -> FileCopyUtils.copy(imageStream, response.outputStream) }
        }
        catch (e: Exception) {
            throw NoSuchElementException(String.format("The User Image %s do not exists", imageName))
        }
    }
}

interface GameService {
    fun findAllGames(): List<GameResponse>
    fun findFirst10ByOrderByRatingDesc(): List<GameResponse>
    fun findTop10ByNameContainingIgnoreCase(name: String): List<GameResponse>
    fun findGameById(id: Long): GameResponse
    fun gameRegistration(gameRegistrationRequest: GameRegistrationRequest): GameResponse
    fun gamesRegistration(gameRegistrationRequests: List<GameRegistrationRequest>): List<GameResponse>
    fun serveGameImage(imageName: String?, response: HttpServletResponse)
}
@Service
class AbstractGameService(
    @Autowired
    val gameRepository: GameRepository,
    @Autowired
    val gameMapper: GameMapper,
    @Autowired
    val platformRepository: PlatformRepository,
    @Autowired
    val genreRepository: GenreRepository,
    @Autowired
    val mappingService: MappingService,
    @Value("\${upload.path}")
    val uploadPath: String,
    @Autowired
    val logService: LogService
): GameService {
    override fun findAllGames(): List<GameResponse> {
        return gameMapper.gamesListToGameResponsesList(gameRepository.findAll(), mappingService)
    }
    override fun findFirst10ByOrderByRatingDesc(): List<GameResponse> {
        return gameMapper.gamesListToGameResponsesList(gameRepository.findFirst10ByOrderByMetacriticDesc(), mappingService)
    }
    override fun findTop10ByNameContainingIgnoreCase(name: String): List<GameResponse> {
        return gameMapper.gamesListToGameResponsesList(gameRepository.findTop10ByNameContainingIgnoreCase(name), mappingService)
    }
    @Throws(NoSuchElementException::class)
    override fun findGameById(id: Long): GameResponse {
        val game: Game = gameRepository.findById(id).orElseThrow {
            NoSuchElementException(String.format("The Game with the Id %d do not exists", id))
        }
        return gameMapper.gameToGameResponse(game, mappingService)
    }
    @Throws(NoSuchElementException::class, ElementAlreadyExists::class)
    override fun gameRegistration(gameRegistrationRequest: GameRegistrationRequest): GameResponse {
        val game: Game? = gameRepository.findBySlug(gameRegistrationRequest.slug).orElse(null)
        if (game != null) {
            throw ElementAlreadyExists(String.format("The Game '%s' already exists", game.name))
        }
        val genresList: List<Genre> = genreRepository.findAllByNameIn(gameRegistrationRequest.gendersNamesList)
        if (genresList.size != gameRegistrationRequest.gendersNamesList.size) {
            throw NoSuchElementException("Not all genders were found")
        }
        val platformsList: List<Platform> = platformRepository.findAllByNameIn(gameRegistrationRequest.platformsNamesList)
        if (platformsList.size != gameRegistrationRequest.platformsNamesList.size) {
            throw NoSuchElementException("Not all platforms were found")
        }
        val platformIdsSet: Set<Long> = platformsList.map { it.id }.toSet()
        val genreIdsSet: Set<Long> = genresList.map { it.id }.toSet()
        val newGame = gameMapper.gameRegistrationRequestToGame(gameRegistrationRequest, GameContext(genreIdsSet, platformIdsSet))
        return gameMapper.gameToGameResponse(gameRepository.save(newGame), mappingService)
    }
    override fun gamesRegistration(gameRegistrationRequests: List<GameRegistrationRequest>): List<GameResponse> {
        val gamesResponsesList: MutableList<GameResponse> = mutableListOf()
        gameRegistrationRequests.forEach { game ->
            gamesResponsesList.add(gameRegistration(game))
        }
        return gamesResponsesList
    }
    @Throws(IOException::class, NoSuchElementException::class)
    override fun serveGameImage(imageName: String?, response: HttpServletResponse) {
        try {
            val imagePath = Paths.get("$uploadPath/games/", imageName).toAbsolutePath().toString()
            response.contentType = "image/png"
            FileInputStream(imagePath).use { imageStream -> FileCopyUtils.copy(imageStream, response.outputStream) }
        }
        catch (e: Exception) {
            throw NoSuchElementException(String.format("The Game Image %s do not exists", imageName))
        }
    }
}

interface PlatformService {
    fun findAllPlatforms(): List<PlatformDetails>
    fun platformRegistration(name: String): PlatformDetails
    fun platformsRegistration(platformsNamesList: List<String>): List<PlatformDetails>
}
@Service
class AbstractPlatformService(
    @Autowired
    val platformRepository: PlatformRepository,
    @Autowired
    val platformMapper: PlatformMapper
): PlatformService {
    override fun findAllPlatforms(): List<PlatformDetails> {
        return platformMapper.platformsListToPlatformsDetailsList(platformRepository.findAll())
    }
    @Transactional(rollbackFor = [ElementAlreadyExists::class])
    @Throws(ElementAlreadyExists::class)
    override fun platformRegistration(name: String): PlatformDetails {
        val platform: Platform? = platformRepository.findByName(name).orElse(null)
        if (platform != null) {
            throw ElementAlreadyExists(String.format("The Platform '%s' already exists"))
        }
        val newPlatform = platformMapper.stringToPlatform(name)
        return platformMapper.platformToPlatformDetails(platformRepository.save(newPlatform))
    }
    override fun platformsRegistration(platformsNamesList: List<String>): List<PlatformDetails> {
        val platformDetailsList: MutableList<PlatformDetails> = mutableListOf()
        platformsNamesList.forEach { platform ->
            platformDetailsList.add(platformRegistration(platform))
        }
        return platformDetailsList
    }

}

interface GenreService {
    fun findAllGenres(): List<GenreDetails>
    fun genreRegistration(name: String): GenreDetails
    fun genresRegistration(genresNamesList: List<String>): List<GenreDetails>
}
@Service
class AbstractGenreService(
    @Autowired
    val genreRepository: GenreRepository,
    @Autowired
    val genreMapper: GenreMapper
): GenreService {
    override fun findAllGenres(): List<GenreDetails> {
        return genreMapper.genresListToGenresDetailsList(genreRepository.findAll())
    }
    @Transactional(rollbackFor = [ElementAlreadyExists::class])
    @Throws(ElementAlreadyExists::class)
    override fun genreRegistration(name: String): GenreDetails {
        val genre: Genre? = genreRepository.findByName(name).orElse(null)
        if (genre != null) {
            throw ElementAlreadyExists(String.format("The Genre '%s' already exists"))
        }
        val newGenre = genreMapper.stringToGenre(name)
        return genreMapper.genreToGenreDetails(genreRepository.save(newGenre))
    }
    override fun genresRegistration(genresNamesList: List<String>): List<GenreDetails> {
        val genreDetailsList: MutableList<GenreDetails> = mutableListOf()
        genresNamesList.forEach { platform ->
            genreDetailsList.add(genreRegistration(platform))
        }
        return genreDetailsList
    }
}

interface GameLogService {
    fun findAllGameLogs(): List<GameLogResponse>
    fun findFirst5ByUserEmailOrderByCreatedDateDesc(email: String): List<GameLogResponse>
    fun findTop5ByUserEmailAndGameNameContainingIgnoreCase(userEmail: String, gameName: String): List<GameLogResponse>
    fun findGameLogById(id: Long): GameLogResponse
    fun gameLogRegistration(gameLogRegistrationRequest: GameLogRegistrationRequest): GameLogResponse
    fun updateGameLogDates(updateGameLogsDatesRequest: UpdateGameLogsDatesRequest): GameLogResponse
    fun updateGameLogFinished(id: Long): GameLogResponse
    fun updateGameLogFinishedAtAll(id: Long): GameLogResponse
    fun deleteGameLog(id: Long): GameLogResponse
}
@Service
class AbstractGameLogService(
    @Autowired
    val gameLogRepository: GameLogRepository,
    @Autowired
    val gameLogMapper: GameLogMapper,
    @Autowired
    val gameRepository: GameRepository,
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val mappingService: MappingService,
    @Autowired
    val logService: LogService,
    @Autowired
    val entityManagerFactory: EntityManagerFactory
): GameLogService {
    override fun findAllGameLogs(): List<GameLogResponse> {
        return gameLogMapper.gameLogsListToGameLogsResponsesList(gameLogRepository.findAll(), mappingService)
    }
    override fun findFirst5ByUserEmailOrderByCreatedDateDesc(email: String): List<GameLogResponse> {
        return gameLogMapper.gameLogsListToGameLogsResponsesList(gameLogRepository.findFirst5ByUserEmailOrderByCreatedDateDesc(email), mappingService)
    }
    override fun findTop5ByUserEmailAndGameNameContainingIgnoreCase(userEmail: String, gameName: String): List<GameLogResponse> {
        return gameLogMapper.gameLogsListToGameLogsResponsesList(gameLogRepository.findTop5ByUserEmailAndGameNameContainingIgnoreCase(
            userEmail, gameName), mappingService)
    }
    @Transactional(rollbackFor = [NoSuchElementException::class])
    @Throws(NoSuchElementException::class)
    override fun findGameLogById(id: Long): GameLogResponse {
        val gameLog: GameLog = gameLogRepository.findById(id).orElseThrow {
            NoSuchElementException(String.format("The Game Log with the id %d do not exists", id))
        }
        return gameLogMapper.gameLogToGameLogResponse(gameLog, mappingService)
    }
    @Transactional(rollbackFor = [NoSuchElementException::class, ElementAlreadyExists::class])
    @Throws(NoSuchElementException::class, ElementAlreadyExists::class)
    override fun gameLogRegistration(gameLogRegistrationRequest: GameLogRegistrationRequest): GameLogResponse {
        val game: Game = gameRepository.findById(gameLogRegistrationRequest.game).orElseThrow {
            NoSuchElementException(String.format("The Game with the id %d do not found",
                gameLogRegistrationRequest.game))
        }
        val user: User = userRepository.findById(gameLogRegistrationRequest.user).orElseThrow {
            NoSuchElementException(String.format("The User with the email %s do not found",
                gameLogRegistrationRequest.user))
        }
        val gameLog: GameLog? = gameLogRepository.findByGameAndUser(game, user).orElse(null)
        if (gameLog != null) {
            throw ElementAlreadyExists("The Game Log Already Exists")
        }
        val newGameLog: GameLog = gameLogMapper.gameAndUserToGameLog(GameLogContext(game, user))
        val response = gameLogMapper.gameLogToGameLogResponse(gameLogRepository.save(newGameLog), mappingService)
        logService.registerLog(
            RegisterLogRequest(
                action = "New Game Log of ${response.game.name} (${response.id})",
                actionType = 1,
                user = response.user.email
            )
        )
        return response
    }
    @Transactional(rollbackFor = [NoSuchElementException::class, BadRequestException::class])
    @Throws(NoSuchElementException::class, BadRequestException::class)
    override fun updateGameLogDates(updateGameLogsDatesRequest: UpdateGameLogsDatesRequest): GameLogResponse {
        val gameLog: GameLog = gameLogRepository.findById(updateGameLogsDatesRequest.id).orElseThrow {
            NoSuchElementException(String.format("The Game Log with the id %d do not found", updateGameLogsDatesRequest.id))
        }
        val oldDate: String
        val date = getDateStringAsDate(updateGameLogsDatesRequest.date, updateGameLogsDatesRequest.time)
        if (date > ZonedDateTime.now()) {
            throw BadRequestException("Date can not be a Future Date")
        }
        when (updateGameLogsDatesRequest.dateToUpdate) {
            "createdDate" -> {
                if (gameLog.finished != null && gameLog.finished!! < date) {
                    throw BadRequestException("Finished Date can not be later than Created Date")
                }
                if (gameLog.finishedAtAll != null && gameLog.finishedAtAll!! < date) {
                    throw BadRequestException("Finished At All Date can not be later than Created Date")
                }
                oldDate = "Created Date from ${gameLog.createdDate}"
                gameLog.createdDate = date
            }
            "finished" -> {
                if (gameLog.createdDate > date) {
                    throw BadRequestException("Created Date can not be later than Finished Date")
                }
                if (gameLog.finishedAtAll != null && gameLog.finishedAtAll!! < date) {
                    throw BadRequestException("Finished At All Date can not be later than Finished Date")
                }
                oldDate = "Finished Date from ${gameLog.finished}"
                gameLog.finished = date
            }
            "finishedAtAll" -> {
                if (gameLog.createdDate > date) {
                    throw BadRequestException("Created Date can not be later than Finished At All Date")
                }
                if (gameLog.finished != null && gameLog.finished!! > date) {
                    throw BadRequestException("Finished Date can not be later than Finished At All Date")
                }
                oldDate = "Finished At All Date from ${gameLog.finishedAtAll}"
                gameLog.finishedAtAll = date
            }
            else -> {
                throw BadRequestException("Date introduced not found")
            }
        }
        val response = gameLogMapper.gameLogToGameLogResponse(gameLogRepository.save(gameLog), mappingService)
        logService.registerLog(
            RegisterLogRequest(
                action = "Update $oldDate to $date of Game Log of ${response.game.name} (${response.id})",
                actionType = 2,
                user = response.user.email
            )
        )
        return response
    }
    @Transactional(rollbackFor = [NoSuchElementException::class])
    @Throws(NoSuchElementException::class)
    override fun updateGameLogFinished(id: Long): GameLogResponse {
        val gameLog: GameLog = gameLogRepository.findById(id).orElseThrow {
            NoSuchElementException(String.format("The Game Log with the id %d do not found", id))
        }
        val oldDate = gameLog.finished
        gameLog.finished = if (gameLog.finished == null) ZonedDateTime.now() else null
        val response = gameLogMapper.gameLogToGameLogResponse(gameLogRepository.save(gameLog), mappingService)
        logService.registerLog(
            RegisterLogRequest(
                action = "Update Finished Date from $oldDate to ${response.finished} of Game Log of ${response.game.name} (${response.id})",
                actionType = 2,
                user = response.user.email
            )
        )
        return response
    }
    @Transactional(rollbackFor = [NoSuchElementException::class])
    @Throws(NoSuchElementException::class)
    override fun updateGameLogFinishedAtAll(id: Long): GameLogResponse {
        val gameLog: GameLog = gameLogRepository.findById(id).orElseThrow {
            NoSuchElementException(String.format("The Game Log with the id %d do not found", id))
        }
        val oldDate = gameLog.finished
        gameLog.finishedAtAll = if (gameLog.finishedAtAll == null) ZonedDateTime.now() else null
        val response = gameLogMapper.gameLogToGameLogResponse(gameLogRepository.save(gameLog), mappingService)
        logService.registerLog(
            RegisterLogRequest(
                action = "Update Finished At All Date from $oldDate to ${response.finishedAtAll} of Game Log of ${response.game.name} (${response.id})",
                actionType = 2,
                user = response.user.email
            )
        )
        return response
    }
    @Transactional(rollbackFor = [NoSuchElementException::class])
    @Throws(NoSuchElementException::class)
    override fun deleteGameLog(id: Long): GameLogResponse {
        val gameLog: GameLog = gameLogRepository.findById(id).orElseThrow {
            NoSuchElementException(String.format("The Game Log with the id %d do not found", id))
        }
        val response = gameLogMapper.gameLogToGameLogResponse(gameLog, mappingService)
        gameLogRepository.delete(gameLog)
        restartAutoIncrement(
            entityManagerFactory = entityManagerFactory,
            sequence = "game_log_id_seq",
            startAt = gameLogRepository.findAll().size + 1
        )
        return response
    }
}

interface LogService {
    fun findAll(): List<LogResponse>
    fun findById(id: Long): LogResponse
    fun registerLog(registerLogRequest: RegisterLogRequest): LogResponse
    fun deleteLog(id: Long): LogResponse
}
@Service
class AbstractLogService(
    @Autowired
    val logRepository: LogRepository,
    @Autowired
    val logMapper: LogMapper,
    @Autowired
    val actionTypeRepository: ActionTypeRepository,
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val mappingService: MappingService,
    @Autowired
    val entityManagerFactory: EntityManagerFactory
): LogService {
    override fun findAll(): List<LogResponse> {
        return logMapper.logsListToLogResponsesList(logRepository.findAll(), mappingService)
    }
    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): LogResponse {
        val log: Log = logRepository.findById(id).orElseThrow {
            NoSuchElementException(String.format("Log with the Id '%d' not found", id))
        }
        return logMapper.logToLogResponse(log, mappingService)
    }
    @Transactional(rollbackFor = [NoSuchElementException::class])
    @Throws(NoSuchElementException::class)
    override fun registerLog(registerLogRequest: RegisterLogRequest): LogResponse {
        val actionType: ActionType = actionTypeRepository.findById(registerLogRequest.actionType).orElseThrow {
            NoSuchElementException(String.format("Action Type with Id '%d' not found", registerLogRequest.actionType))
        }
        val user: User = userRepository.findById(registerLogRequest.user).orElseThrow {
            NoSuchElementException(String.format("User with Email '%s' not found", registerLogRequest.user))
        }
        val newLog = logMapper.registerLogRequestToLog(registerLogRequest, actionType, user)
        return logMapper.logToLogResponse(logRepository.save(newLog), mappingService)
    }
    @Transactional(rollbackFor = [NoSuchElementException::class])
    @Throws(NoSuchElementException::class)
    override fun deleteLog(id: Long): LogResponse {
        val log: Log = logRepository.findById(id).orElseThrow {
            NoSuchElementException(String.format("The Game Log with the id %d do not found", id))
        }
        val response = logMapper.logToLogResponse(log, mappingService)
        logRepository.delete(log)
        restartAutoIncrement(
            entityManagerFactory = entityManagerFactory,
            sequence = "logs_id_seq",
            startAt = logRepository.findAll().size + 1
        )
        return response
    }
}

interface MappingService {
    fun findAllGenresByGameId(list: Set<Long>): Set<GenreDetails>
    fun findAllPlatformsByGameId(list: Set<Long>): Set<PlatformDetails>
    fun findAllGameLogsByUser(email: String): UserGamesLogsResponse
    fun findRoleByIdToMapper(id: Long): Role
}
@Service
class AbstractMappingService(
    @Autowired
    val roleRepository: RoleRepository,
    @Autowired
    val genreRepository: GenreRepository,
    @Autowired
    val genreMapper: GenreMapper,
    @Autowired
    val platformRepository: PlatformRepository,
    @Autowired
    val platformMapper: PlatformMapper,
    @Autowired
    val userRepository: UserRepository
) : MappingService {
    override fun findAllGenresByGameId(list: Set<Long>): Set<GenreDetails> {
        return genreMapper.genresListToGenresDetailsList(genreRepository.findAllById(list)).toSet()
    }
    override fun findAllPlatformsByGameId(list: Set<Long>): Set<PlatformDetails> {
        return platformMapper.platformsListToPlatformsDetailsList(platformRepository.findAllById(list)).toSet()
    }
    @Throws(NoSuchElementException::class)
    override fun findAllGameLogsByUser(email: String): UserGamesLogsResponse {
        var finished = 0
        var finishedAtAll = 0
        val user: User = userRepository.findById(email).orElseThrow {
            NoSuchElementException(String.format("The User with the email %s does not exists", email))
        }
        val totalGames = user.gamesLogsList.size
        user.gamesLogsList.forEach { gameLog ->
            if (gameLog.finished != null) {
                finished++
                if (gameLog.finishedAtAll != null) {
                    finishedAtAll++
                }
            }
        }
        return UserGamesLogsResponse(totalGames, totalGames - finished, finished, finishedAtAll)
    }
    override fun findRoleByIdToMapper(id: Long): Role {
        return roleRepository.findById(id).orElse(null)
    }
}

interface BackupService {
    fun backupGameLogs(): List<BackupUserGameLogsDetails>
    fun saveGameLogs(backupUserGameLogsDetailsList: List<BackupUserGameLogsDetails>): List<GameLogResponse>
    fun deleteGameLogs(): List<GameLogResponse>
}
@Service
class AbstractBackupService(
    @Autowired
    val gameLogRepository: GameLogRepository,
    @Autowired
    val gameLogMapper: GameLogMapper,
    @Autowired
    val gameRepository: GameRepository,
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val mappingService: MappingService,
    @Autowired
    val entityManagerFactory: EntityManagerFactory
): BackupService {
    override fun backupGameLogs(): List<BackupUserGameLogsDetails> {
        return userRepository.findAll().map { user ->
            BackupUserGameLogsDetails(
                gamesList = gameLogRepository.findByUserEmailOrderByCreatedDateAsc(user.email).map { gameLog -> gameLog.game.slug },
                user = user.email
            )
        }
    }
    @Transactional(rollbackFor = [ElementAlreadyExists::class])
    @Throws(ElementAlreadyExists::class)
    override fun saveGameLogs(backupUserGameLogsDetailsList: List<BackupUserGameLogsDetails>): List<GameLogResponse> {
        val gamesLogsResponsesList: MutableList<GameLogResponse> = mutableListOf()
        backupUserGameLogsDetailsList.forEach {
            val user: User = userRepository.findById(it.user).orElseThrow {
                NoSuchElementException(String.format("User with the email %s not found", it.user))
            }
            val gamesList = it.gamesList.map { gameSlug ->
                gameRepository.findBySlug(gameSlug).orElseThrow {
                    NoSuchElementException(String.format("Game with slug '%s' not found", gameSlug))
                }
            }
            gamesList.forEach { game ->
                val gameLog: GameLog? = gameLogRepository.findByGameAndUser(game, user).orElse(null)
                if (gameLog != null) {
                    throw ElementAlreadyExists(
                        String.format("The Game Log with game %s and user '%s' already exists", game.name, user.email)
                    )
                }
                val newGameLog = gameLogRepository.save(gameLogMapper.gameAndUserToGameLog(GameLogContext(game, user)))
                gamesLogsResponsesList.add(gameLogMapper.gameLogToGameLogResponse(newGameLog, mappingService))
            }
        }
        return gamesLogsResponsesList
    }
    override fun deleteGameLogs(): List<GameLogResponse> {
        gameLogRepository.deleteAll()
        restartAutoIncrement(
            entityManagerFactory = entityManagerFactory,
            sequence = "game_log_id_seq",
            startAt = 1
        )
        return gameLogMapper.gameLogsListToGameLogsResponsesList(gameLogRepository.findAll(), mappingService)
    }
}

// Tag that establishes that this class is a Spring Service
@Service
// Tag that establishes that is a Transactional Service. This one makes a transaction when
// this service is in operation.
@Transactional
// AppUserDetailsService Service Class
class AppUserDetailsService(
    // Variables Declaration to use in functions
    @Autowired
    val userRepository: UserRepository,
) : UserDetailsService {
    // Tag that allows to throw a Username Not Found Exception
    @Throws(UsernameNotFoundException::class)
    // Function that is used to the user details during the authentication
    override fun loadUserByUsername(email: String): UserDetails {
        val user: User = userRepository.findById(email).orElse(null)
            ?: return org.springframework.security.core.userdetails.User(
                "Error", "Error", true, true, true, true,
                emptyList()
            )
        var password: String? = ""
        if (user.password != null) {
            password = user.password
        }
        // Returns a Spring Security "User" with the "User" information found
        return org.springframework.security.core.userdetails.User(
            user.email, password, user.enabled, true, true,
            true, emptyList()
        )
    }
}