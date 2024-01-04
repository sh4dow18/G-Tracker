package sh4dow18.gtracker.backend_spring

import com.fasterxml.jackson.annotation.JsonCreator

// Requests

// Register

data class UserRegistrationRequest(
    var email: String,
    var userName: String,
    var password: String
)

data class GameRegistrationRequest(
    var name: String,
    var slug: String,
    var rating: Float,
    var metacritic: Int,
    var releaseDate: String,
    var imageUrl: String,
    var gendersNamesList: List<String>,
    var platformsNamesList: List<String>
)

data class GameLogRegistrationRequest(
    var game: Long,
    var user: String
)

data class RegisterLogRequest(
    var action: String,
    var actionType: Long,
    var user: String
)

// Update

data class UpdateUserRequest(
    var email: String,
    var userName: String
)

data class CloseAccountRequest(
    var email: String,
    var enabled: Boolean
)

data class UpdateGameLogsDatesRequest(
    var id: Long,
    var dateToUpdate: String,
    var date: String,
    var time: String
)

// Auth

data class UserLoginRequest(
    var email: String,
    var password: String
){
    @JsonCreator
    constructor() : this("", "")
}

// Responses

data class UserResponse(
    var email: String,
    var userName: String,
    var createdDate: String,
    var enabled: Boolean,
    var image: Boolean,
    var role: RoleDetails,
    var gameLogs: UserGamesLogsResponse
)

data class GameResponse(
    var id: Long,
    var name: String,
    var slug: String,
    var rating: Float,
    var metacritic: Int,
    var releaseDate: String,
    var imageUrl: String,
    var gendersList: Set<GenreDetails>,
    var platformsList: Set<PlatformDetails>
)

data class GameLogResponse(
    var id: Long,
    var createdDate: String,
    var finished: String?,
    var finishedAtAll: String?,
    var game: GameResponse,
    var user: UserResponse
)

data class UserGamesLogsResponse(
    var total: Int,
    var notFinished: Int,
    var finished: Int,
    var finishedAtAll: Int
)

data class LogResponse(
    var id: Long,
    var action: String,
    var actionType: ActionTypeResponse,
    var user: UserResponse
)

data class ActionTypeResponse(
    var id: Long,
    var name: String
)

// Details

data class RoleDetails(
    var id: Long,
    var name: String
)

data class GenreDetails(
    var id: Long,
    var name: String
)

data class PlatformDetails(
    var id: Long,
    var name: String
)

data class BackupUserGameLogsDetails(
    var gamesList: List<String>,
    var user: String
)

// Extras

data class GameContext(
    val genresList: Set<Long>,
    val platformsList: Set<Long>
)

data class GameLogContext(
    var game: Game,
    var user: User
)