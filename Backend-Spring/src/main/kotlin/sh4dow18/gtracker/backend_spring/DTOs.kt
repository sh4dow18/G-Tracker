package sh4dow18.gtracker.backend_spring

import com.fasterxml.jackson.annotation.JsonCreator
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

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

data class UpdateUserRequest(
    var email: String,
    var userName: String
)

data class CloseAccountRequest(
    var email: String,
    var enabled: Boolean
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

// Responses

data class UserResponse(
    var email: String,
    var userName: String,
    var password: String?,
    var createdDate: String,
    var enabled: Boolean,
    var imagePath: String?,
    var role: RoleDetails,
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

data class GameContext(
    val genresList: Set<Long>,
    val platformsList: Set<Long>
)

data class GameLogContext(
    var game: Game,
    var user: User
)