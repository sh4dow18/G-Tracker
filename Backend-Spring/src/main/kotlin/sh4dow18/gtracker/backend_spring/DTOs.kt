package sh4dow18.gtracker.backend_spring

import com.fasterxml.jackson.annotation.JsonCreator

// Requests

data class UserRegistrationRequest(
    var email: String,
    var userName: String,
    var password: String
)

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
    var password: String,
    var createdDate: String,
    var enabled: Boolean,
    var imagePath: String?,
    var role: RoleDetails,
)

// Details

data class RoleDetails(
    var id: Long,
    var name: String
)