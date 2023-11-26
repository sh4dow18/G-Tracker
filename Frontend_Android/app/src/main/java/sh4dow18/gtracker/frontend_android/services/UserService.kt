package sh4dow18.gtracker.frontend_android.services

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import sh4dow18.gtracker.frontend_android.utils.UserLoginRequest
import sh4dow18.gtracker.frontend_android.utils.UserLoginResponse
import sh4dow18.gtracker.frontend_android.utils.UserRegistrationRequest
import sh4dow18.gtracker.frontend_android.utils.UserResponse

interface UserService {

    @GET("api/users/{id}")
    suspend fun findUserById(@Path("id") id: String): Response<UserResponse>

    @POST("api/public/users")
    suspend fun userRegistration(@Body userRegistrationRequest: UserRegistrationRequest): Response<UserResponse>

    @POST("api/login")
    suspend fun userLogin(@Body userLoginRequest: UserLoginRequest): Response<UserLoginResponse>

    @PUT("api/users")
    suspend fun updateUser(@Body formData: MultipartBody): Response<UserResponse>

    @PUT("api/users/{id}")
    suspend fun closeAccount(@Path("id") id: String): Response<UserResponse>

    companion object {
        private var service: UserService? = null
        fun getInstance(): UserService {
            if (service == null) {
                service = ServiceBuilder.buildService(UserService::class.java)
            }
            return service!!
        }
    }
}