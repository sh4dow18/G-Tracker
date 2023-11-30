package sh4dow18.gtracker.frontend_android.services

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import sh4dow18.gtracker.frontend_android.utils.GameResponse

interface GameService {

    @GET("api/games/first20")
    suspend fun findFirst20ByOrderByRatingDesc(): Response<List<GameResponse>>

    @GET("api/games/search/{name}")
    suspend fun findByNameContainingIgnoreCase(@Path("name") name: String): Response<List<GameResponse>>

    @GET("api/games/{id}")
    suspend fun findById(@Path("id") id: Long): Response<GameResponse>

    companion object {
        private var service: GameService? = null
        fun getInstance(): GameService {
            if (service == null) {
                service = ServiceBuilder.buildService(GameService::class.java)
            }
            return service!!
        }
    }
}