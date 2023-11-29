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

// Responses

data class UserResponse(
    var email: String,
    var userName: String,
    var password: String,
    var createdDate: String,
    var enabled: Boolean,
    var imagePath: String?,
    var role: RoleDetails,
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
    var description: String,
    var rating: Int,
    var gendersList: List<GenderResponse>,
    var releaseDate: String
)

data class GenderResponse(
    var id: Long,
    var name: String
)

// Details

data class RoleDetails(
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