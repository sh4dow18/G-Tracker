package sh4dow18.gtracker.frontend_android.services

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import sh4dow18.gtracker.frontend_android.utils.GameLogRegistrationRequest
import sh4dow18.gtracker.frontend_android.utils.GameLogResponse
import sh4dow18.gtracker.frontend_android.utils.UpdateGameLogsDatesRequest

interface GameLogService {

    @GET("api/gameLogs/user/{email}")
    suspend fun findFirst5ByUserEmail(@Path("email") email: String): Response<List<GameLogResponse>>

    @GET("api/gameLogs/{id}")
    suspend fun findGameLogById(@Path("id") id: Long): Response<GameLogResponse>

    @GET("api/gameLogs/user/search")
    suspend fun findTop5ByUserEmailAndGameName(
        @Query("userEmail") userEmail: String,
        @Query("gameName") gameName: String
    ): Response<List<GameLogResponse>>

    @POST("api/gameLogs")
    suspend fun gameLogRegistration(@Body gameLogRegistrationRequest: GameLogRegistrationRequest): Response<GameLogResponse>

    @PUT("api/gameLogs")
    suspend fun updateGameLogDates(@Body updateGameLogsDatesRequest: UpdateGameLogsDatesRequest): Response<GameLogResponse>

    @PUT("api/gameLogs/finished/{id}")
    suspend fun updateGameLogFinished(@Path("id") id: Long): Response<GameLogResponse>

    @PUT("api/gameLogs/finishedAtAll/{id}")
    suspend fun updateGameLogFinishedAtAll(@Path("id") id: Long): Response<GameLogResponse>

    @DELETE("api/gameLogs/{id}")
    suspend fun deleteGameLog(@Path("id") id: Long): Response<GameLogResponse>

    companion object {
        private var service: GameLogService? = null
        fun getInstance(): GameLogService {
            if (service == null) {
                service = ServiceBuilder.buildService(GameLogService::class.java)
            }
            return service!!
        }
    }
}