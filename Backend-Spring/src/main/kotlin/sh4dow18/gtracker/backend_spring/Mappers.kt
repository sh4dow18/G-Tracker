package sh4dow18.gtracker.backend_spring

import org.mapstruct.BeanMapping
import org.mapstruct.Context
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

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
    @Mapping(target = "createdDate", expression = "java(user.getCreatedDate().format(java.time.format.DateTimeFormatter.ofPattern(\"yyyy-MM-dd\")))")
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
    @Mapping(target = "createdDate", expression = "java(java.time.ZonedDateTime.now(java.time.ZoneOffset.UTC))")
    @Mapping(target = "finished", expression = "java(false)")
    @Mapping(target = "finishedAtAll", expression = "java(false)")
    fun gameAndUserToGameLog(
        gameLogContext: GameLogContext,
    ): GameLog
    @Mapping(target = "createdDate", expression = "java(gameLog.getCreatedDate().format(java.time.format.DateTimeFormatter.ofPattern(\"yyyy-MM-dd\")))")
    @Mapping(target = "game.gendersList", expression = "java(mappingService.findAllGenresByGameId(game.getGenderGame()))")
    @Mapping(target = "game.platformsList", expression = "java(mappingService.findAllPlatformsByGameId(game.getPlatformGame()))")
    @Mapping(target = "user.gameLogs", expression = "java(mappingService.findAllGameLogsByUser(user.getEmail()))")
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