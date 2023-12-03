package sh4dow18.gtracker.frontend_android.utils

// Requests

data class UserRegistrationRequest(
    var email: String,
    var userName: String,
    var password: String
)

data class UserLoginRequest(
    var email: String,
    var password: String
)

data class UpdateUserRequest(
    var email: String,
    var userName: String
)

data class GameLogRegistrationRequest(
    var game: Long,
    var user: String
)

data class GameLogUpdateRequest(
    var id: Long,
    var finished: Boolean,
    var finishedAtAll: Boolean
)

// Responses

data class UserResponse(
    var email: String,
    var userName: String,
    var password: String,
    var createdDate: String,
    var enabled: Boolean,
    var image: Boolean,
    var role: RoleDetails,
    var gameLogs: UserGamesLogsResponse
)

data class UserLoginResponse(
    var username: String,
    var password: String,
    var authorities: List<String>,
    var accountNonExpired: Boolean,
    var accountNonLocked: Boolean,
    var credentialsNonExpired: Boolean,
    var enabled: Boolean,
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
    var finished: Boolean,
    var finishedAtAll: Boolean,
    var game: GameResponse,
    var user: UserResponse
)

data class UserGamesLogsResponse(
    var total: Int,
    var notFinished: Int,
    var finished: Int,
    var finishedAtAll: Int
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

// Extras

data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)

data class LoggedInUserView(
    val username: String
)

data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)