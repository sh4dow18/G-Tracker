package sh4dow18.gtracker.backend_spring

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
    val uploadPath: String
): UserService {
    @Throws(NoSuchElementException::class)
    override fun findUserById(email: String): UserResponse {
        val user: User = userRepository.findById(email).orElse(null)
            ?: throw NoSuchElementException(String.format("The User with the email %s does not exists", email))
        return userMapper.userToUserResponse(user)
    }
    @Throws(ElementAlreadyExists::class)
    override fun userRegistration(userRegistrationRequest: UserRegistrationRequest): UserResponse {
        val user: User? = userRepository.findById(userRegistrationRequest.email).orElse(null)
        if (user != null) {
            throw ElementAlreadyExists(String.format("The User with the email %s already exists", userRegistrationRequest.email))
        }
        val newUser = userMapper.userRegistrationRequestToUser(userRegistrationRequest, mappingService, passwordEncoder())
        return userMapper.userToUserResponse(userRepository.save(newUser))
    }
    @Throws(NoSuchElementException::class, BadRequestException::class)
    override fun updateUser(updateUserRequest: UpdateUserRequest, image: MultipartFile?): UserResponse {
        val user: User = userRepository.findById(updateUserRequest.email).orElse(null)
            ?: throw NoSuchElementException(String.format("The User with the email %s does not exists", updateUserRequest.email))
        userMapper.updateUser(updateUserRequest, user)
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
            // Update the imagePath
            user.imagePath = uniqueFileName
        }
        return userMapper.userToUserResponse(userRepository.save(user))
    }
    @Throws(NoSuchElementException::class)
    override fun closeAccount(email: String): UserResponse {
        val user: User = userRepository.findById(email).orElse(null)
            ?: throw NoSuchElementException(String.format("The User with the email %s does not exists", email))
        user.password = null
        user.enabled = false
        return userMapper.userToUserResponse(userRepository.save(user))
    }
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
    fun findFirst20ByOrderByRatingDesc(): List<GameResponse>
    fun findByNameContainingIgnoreCase(name: String): List<GameResponse>
    fun findGameById(id: Long): GameResponse
    fun gameRegistration(gameRegistrationRequest: GameRegistrationRequest): GameResponse
    fun gamesRegistration(gameRegistrationRequests: List<GameRegistrationRequest>): List<GameResponse>
    fun createImageFromGameUrl(id: Long): Boolean
    fun createImagesFromGamesUrls(): Boolean
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
    val uploadPath: String
): GameService {
    override fun findAllGames(): List<GameResponse> {
        return gameMapper.gamesListToGameResponsesList(gameRepository.findAll(), mappingService)
    }
    override fun findFirst20ByOrderByRatingDesc(): List<GameResponse> {
        return gameMapper.gamesListToGameResponsesList(gameRepository.findFirst20ByOrderByMetacriticDesc(), mappingService)
    }
    override fun findByNameContainingIgnoreCase(name: String): List<GameResponse> {
        return gameMapper.gamesListToGameResponsesList(gameRepository.findByNameContainingIgnoreCase(name), mappingService)
    }
    @Throws(NoSuchElementException::class)
    override fun findGameById(id: Long): GameResponse {
        val game: Game = gameRepository.findById(id).orElseThrow {
            NoSuchElementException(String.format("The Game with the Id %d do not exists", id))
        }
        return gameMapper.gameToGameResponse(game, mappingService)
    }
    @Throws(NoSuchElementException::class)
    override fun gameRegistration(gameRegistrationRequest: GameRegistrationRequest): GameResponse {
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
        val game = gameMapper.gameRegistrationRequestToGame(gameRegistrationRequest, GameContext(genreIdsSet, platformIdsSet))
        return gameMapper.gameToGameResponse(gameRepository.save(game), mappingService)
    }

    override fun gamesRegistration(gameRegistrationRequests: List<GameRegistrationRequest>): List<GameResponse> {
        val gamesResponsesList: MutableList<GameResponse> = mutableListOf()
        gameRegistrationRequests.forEach { game ->
            gamesResponsesList.add(gameRegistration(game))
        }
        return gamesResponsesList
    }
    @Throws(NoSuchElementException::class)
    override fun createImageFromGameUrl(id: Long): Boolean {
        val game: Game = gameRepository.findById(id).orElseThrow {
            NoSuchElementException(String.format("The Game with the id %d does not exists", id))
        }
        if (game.imageUrl != "") {
            val originalImage = ImageIO.read(URL(game.imageUrl))
                ?: throw NoSuchElementException(String.format("The Image with the URL %s was not found", game.imageUrl))
            val targetWidth = 95
            val targetHeight = 95
            val xScale = targetWidth.toDouble() / originalImage.width
            val yScale = targetHeight.toDouble() / originalImage.height
            val scale = if (xScale > yScale) xScale else yScale
            val newWidth = (originalImage.width * scale).toInt()
            val newHeight = (originalImage.height * scale).toInt()
            val resizedImage = BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB)
            val graphics = resizedImage.createGraphics()
            graphics.drawImage(originalImage, (targetWidth - newWidth) / 2, (targetHeight - newHeight) / 2, newWidth, newHeight, null)
            graphics.dispose()
            val uniqueFileName = "${game.id}.png"
            // Renders the image as "jpg" and save it in the path "uploadPath"
            ImageIO.write(resizedImage, "png", File("$uploadPath/games/$uniqueFileName"))
        }
        return true
    }
    @Throws(NoSuchElementException::class)
    override fun createImagesFromGamesUrls(): Boolean {
        gameRepository.findAll().forEach { game ->
            createImageFromGameUrl(game.id)
        }
        return true
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
    override fun platformRegistration(name: String): PlatformDetails {
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
    override fun genreRegistration(name: String): GenreDetails {
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
    fun findAllByUserEmail(email: String): List<GameLogResponse>
    fun findGameLogById(id: Long): GameLogResponse
    fun gameLogRegistration(gameLogRegistrationRequest: GameLogRegistrationRequest): GameLogResponse
    fun gameLogUpdateFinished(id: Long): GameLogResponse
    fun gameLogUpdateFinishedAtAll(id: Long): GameLogResponse
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
    val mappingService: MappingService
): GameLogService {
    override fun findAllGameLogs(): List<GameLogResponse> {
        return gameLogMapper.gameLogsListToGameLogsResponsesList(gameLogRepository.findAll(), mappingService)
    }
    override fun findAllByUserEmail(email: String): List<GameLogResponse> {
        return gameLogMapper.gameLogsListToGameLogsResponsesList(gameLogRepository.findAllByUserEmailOrderByCreatedDateDesc(email), mappingService)
    }
    @Throws(NoSuchElementException::class)
    override fun findGameLogById(id: Long): GameLogResponse {
        val gameLog: GameLog = gameLogRepository.findById(id).orElseThrow {
            NoSuchElementException(String.format("The Game Log with the id %d do not exists", id))
        }
        return gameLogMapper.gameLogToGameLogResponse(gameLog, mappingService)
    }
    @Throws(NoSuchElementException::class, ElementAlreadyExists::class)
    override fun gameLogRegistration(gameLogRegistrationRequest: GameLogRegistrationRequest): GameLogResponse {
        val game: Game = gameRepository.findById(gameLogRegistrationRequest.game).orElse(null)
            ?: throw NoSuchElementException(String.format("The Game with the id %d do not found",
                gameLogRegistrationRequest.game))
        val user: User = userRepository.findById(gameLogRegistrationRequest.user).orElse(null)
            ?: throw NoSuchElementException(String.format("The User with the email %s do not found",
                gameLogRegistrationRequest.user))
        val gameLog: GameLog? = gameLogRepository.findByGameAndUser(game, user).orElse(null)
        if (gameLog != null) {
            ElementAlreadyExists(String.format("The Game Log with game %s and user %s already exists", game.name, user.userName))
        }
        val newGameLog: GameLog = gameLogMapper.gameAndUserToGameLog(GameLogContext(game, user))
        return gameLogMapper.gameLogToGameLogResponse(gameLogRepository.save(newGameLog), mappingService)
    }
    @Throws(NoSuchElementException::class)
    override fun gameLogUpdateFinished(id: Long): GameLogResponse {
        val gameLog: GameLog = gameLogRepository.findById(id).orElse(null)
            ?: throw NoSuchElementException(String.format("The Game Log with the id %d do not found", id))
        gameLog.finished = !gameLog.finished
        return gameLogMapper.gameLogToGameLogResponse(gameLogRepository.save(gameLog), mappingService)
    }
    @Throws(NoSuchElementException::class)
    override fun gameLogUpdateFinishedAtAll(id: Long): GameLogResponse {
        val gameLog: GameLog = gameLogRepository.findById(id).orElse(null)
            ?: throw NoSuchElementException(String.format("The Game Log with the id %d do not found", id))
        gameLog.finishedAtAll = !gameLog.finishedAtAll
        return gameLogMapper.gameLogToGameLogResponse(gameLogRepository.save(gameLog), mappingService)
    }
}

interface MappingService {
    fun findRoleByIdToMapper(id: Long): Role
    fun findAllGenresByGameId(list: Set<Long>): Set<GenreDetails>
    fun findAllPlatformsByGameId(list: Set<Long>): Set<PlatformDetails>
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
    val platformMapper: PlatformMapper
) : MappingService {
    override fun findRoleByIdToMapper(id: Long): Role {
        return roleRepository.findById(id).orElse(null)
    }
    override fun findAllGenresByGameId(list: Set<Long>): Set<GenreDetails> {
        return genreMapper.genresListToGenresDetailsList(genreRepository.findAllById(list)).toSet()
    }
    override fun findAllPlatformsByGameId(list: Set<Long>): Set<PlatformDetails> {
        return platformMapper.platformsListToPlatformsDetailsList(platformRepository.findAllById(list)).toSet()
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
        // Check if an "AccessPageCategory" with the given "id" already exists, if not exists,
        // returns a Spring Security User
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