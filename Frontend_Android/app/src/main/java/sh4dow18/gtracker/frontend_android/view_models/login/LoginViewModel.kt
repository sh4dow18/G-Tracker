package sh4dow18.gtracker.frontend_android.view_models.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sh4dow18.gtracker.frontend_android.R
import sh4dow18.gtracker.frontend_android.repositories.LoginRepository
import sh4dow18.gtracker.frontend_android.utils.LoggedInUserView
import sh4dow18.gtracker.frontend_android.utils.LoginFormState
import sh4dow18.gtracker.frontend_android.utils.LoginResult
import sh4dow18.gtracker.frontend_android.utils.UserLoginRequest

class LoginViewModel constructor(
    private val loginRepository: LoginRepository,
) : ViewModel(){

    private var job: Job? = null
    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResponse = MutableLiveData<LoginResult>()
    val loginResponse : LiveData<LoginResult> = _loginResponse

    fun login(loginRequest: UserLoginRequest) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            try {
                val response = loginRepository.login(loginRequest)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        _loginResponse.value =
                            LoginResult(success = LoggedInUserView(username = response.body()?.username ?: ""))
                        loading.value = false
                    } else {
                        _loginResponse.value = LoginResult(error = R.string.login_failed)
                        onError("Error : ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _loginResponse.value = LoginResult(error = R.string.server_unavailable)
                    onError("Server Unavailable")
                }
            }
        }
    }

    fun logout() {
        loginRepository.logout()
    }

    fun getUsername() : String {
        return loginRepository.getUser()
    }

    fun loginDataChanged(loginRequest: UserLoginRequest) {
        if (!isUserNameValid(loginRequest.email)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_email)
        } else if (!isPasswordValid(loginRequest.password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.contains(Regex("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\$"))
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 10 && password.contains(Regex("^[^\\\\'\"=;%<>&?+]+\$"))
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}