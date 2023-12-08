package sh4dow18.gtracker.frontend_android.repositories

import retrofit2.Response
import sh4dow18.gtracker.frontend_android.services.UserService
import sh4dow18.gtracker.frontend_android.utils.MyApplication
import sh4dow18.gtracker.frontend_android.utils.UserLoginRequest
import sh4dow18.gtracker.frontend_android.utils.UserLoginResponse

class LoginRepository constructor(private val loginService: UserService) {
    private var user: UserLoginResponse? = null

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    fun logout(){
        user = null
        MyApplication.sessionManager?.deleteAuthToken()
    }

    suspend fun login(userLogin: UserLoginRequest) : Response<UserLoginResponse> {
        val response = loginService.userLogin(userLogin)
        if (response.isSuccessful) {
            setLoggedInUser(response.body(), response.headers()["Authorization"].toString())
        }
        return response
    }

    private fun setLoggedInUser(loginRequest: UserLoginResponse?, token: String){
        this.user = loginRequest
        MyApplication.sessionManager?.saveAuthToken(token)
    }

    fun getUser(): String {
        return user?.username!!
    }
}