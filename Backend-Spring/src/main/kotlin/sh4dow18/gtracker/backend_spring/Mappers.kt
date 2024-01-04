package sh4dow18.gtracker.backend_spring

import org.mapstruct.BeanMapping
import org.mapstruct.Context
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.swing.Action

const val UTILS_PATH = "sh4dow18.gtracker.backend_spring.UtilsKt"

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserMapper {
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRegistrationRequest.getPassword()))")
    @Mapping(target = "createdDate", expression = "java(java.time.ZonedDateTime.now())")
    @Mapping(target = "enabled", expression = "java(true)")
    @Mapping(target = "image", expression = "java(false)")
    @Mapping(target = "role", expression = "java(mappingService.findRoleByIdToMapper(1))")
    @Mapping(target = "gamesLogsList", expression = "java(java.util.Collections.emptyList())")
    @Mapping(target = "logsList", expression = "java(java.util.Collections.emptyList())")
    fun userRegistrationRequestToUser(
        userRegistrationRequest: UserRegistrationRequest,
        @Context mappingService: MappingService,
        @Context passwordEncoder: BCryptPasswordEncoder,
    ): User
    @Mapping(target = "createdDate", expression = "java($UTILS_PATH.getDateAsString(user.getCreatedDate(), true))")
    @Mapping(target = "gameLogs", expression = "java(mappingService.findAllGameLogsByUser(user.getEmail()))")
    fun userToUserResponse(
        user: User,
        @Context mappingService: MappingService
    ): UserResponse
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateUser(dto: UpdateUserRequest, @MappingTarget user: User)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun closeAccount(dto: CloseAccountRequest, @MappingTarget user: User)
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface GameMapper {
    @Mapping(target = "gamesLogsList", expression = "java(java.util.Collections.emptyList())")
    @Mapping(target = "platformGame", expression = "java(myContext.getPlatformsList())")
    @Mapping(target = "genderGame", expression = "java(myContext.getGenresList())")
    fun gameRegistrationRequestToGame(
        gameRegistrationRequest: GameRegistrationRequest,
        @Context myContext: GameContext
    ): Game
    @Mapping(target = "gendersList", expression = "java(mappingService.findAllGenresByGameId(game.getGenderGame()))")
    @Mapping(target = "platformsList", expression = "java(mappingService.findAllPlatformsByGameId(game.getPlatformGame()))")
    fun gameToGameResponse(
        game: Game,
        @Context mappingService: MappingService
    ): GameResponse
    fun gamesListToGameResponsesList(
        gamesList: List<Game>,
        @Context mappingService: MappingService
    ): List<GameResponse> {
        return gamesList.map { gameToGameResponse(it, mappingService) }
    }
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface PlatformMapper {
    @Mapping(target = "name", expression = "java(platformName)")
    fun stringToPlatform(
        platformName: String
    ): Platform
    fun platformToPlatformDetails(
        platform: Platform
    ): PlatformDetails
    fun platformsListToPlatformsDetailsList(
        platformsList: List<Platform>
    ): List<PlatformDetails>
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface GenreMapper {
    @Mapping(target = "name", expression = "java(genreName)")
    fun stringToGenre(
        genreName: String
    ): Genre
    fun genreToGenreDetails(
        genre: Genre
    ): GenreDetails
    fun genresListToGenresDetailsList(
        genresList: List<Genre>
    ): List<GenreDetails>
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface GameLogMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.ZonedDateTime.now())")
    @Mapping(target = "finished", expression = "java(null)")
    @Mapping(target = "finishedAtAll", expression = "java(null)")
    fun gameAndUserToGameLog(
        gameLogContext: GameLogContext,
    ): GameLog
    @Mapping(target = "createdDate", expression = "java($UTILS_PATH.getDateAsString(gameLog.getCreatedDate(), true))")
    @Mapping(target = "finished", expression = "java($UTILS_PATH.getDateAsString(gameLog.getFinished(), true))")
    @Mapping(target = "finishedAtAll", expression = "java($UTILS_PATH.getDateAsString(gameLog.getFinishedAtAll(), true))")
    @Mapping(target = "game.gendersList", expression = "java(mappingService.findAllGenresByGameId(game.getGenderGame()))")
    @Mapping(target = "game.platformsList", expression = "java(mappingService.findAllPlatformsByGameId(game.getPlatformGame()))")
    @Mapping(target = "user.gameLogs", expression = "java(mappingService.findAllGameLogsByUser(user.getEmail()))")
    @Mapping(target = "user.createdDate", expression = "java($UTILS_PATH.getDateAsString(user.getCreatedDate(), true))")
    fun gameLogToGameLogResponse(
        gameLog: GameLog,
        @Context mappingService: MappingService
    ): GameLogResponse
    fun gameLogsListToGameLogsResponsesList(
        gameLogsList: List<GameLog>,
        @Context mappingService: MappingService
    ): List<GameLogResponse> {
        return gameLogsList.map { gameLogToGameLogResponse(it, mappingService) }
    }
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LogMapper {
    @Mapping(target = "actionType", expression = "java(newActionType)")
    @Mapping(target = "user", expression = "java(newUser)")
    fun registerLogRequestToLog(
        registerLogRequest: RegisterLogRequest,
        newActionType: ActionType,
        newUser: User
    ): Log
    @Mapping(target = "user.gameLogs", expression = "java(mappingService.findAllGameLogsByUser(user.getEmail()))")
    fun logToLogResponse(
        log: Log,
        @Context mappingService: MappingService
    ): LogResponse
    fun logsListToLogResponsesList(
        logsList: List<Log>,
        @Context mappingService: MappingService
    ): List<LogResponse> {
        return logsList.map { logToLogResponse(it, mappingService) }
    }
}