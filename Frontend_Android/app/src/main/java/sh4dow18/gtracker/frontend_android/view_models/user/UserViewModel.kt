package sh4dow18.gtracker.frontend_android.view_models.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sh4dow18.gtracker.frontend_android.repositories.UserRepository
import sh4dow18.gtracker.frontend_android.utils.UserRegistrationRequest
import sh4dow18.gtracker.frontend_android.utils.UserResponse

sealed class StateUser {
    data object Loading : StateUser()
    data class Success(val user: UserResponse?) : StateUser()
    data class Error(val message: String) : StateUser()
}

class UserViewModel constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _state = MutableLiveData<StateUser>()
    val state: LiveData<StateUser> get() = _state
    private var job: Job? = null
    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun findUserById(email: String) {
        _state.value = StateUser.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = userRepository.findUserById(email)
            withContext(Dispatchers.Main) {
                _state.postValue(
                    if (response.isSuccessful) StateUser.Success(response.body())
                    else StateUser.Error("Error : ${response.message()} ")
                )
            }
        }
    }

    fun userRegistration(userRegistrationRequest: UserRegistrationRequest) {
        _state.value = StateUser.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = userRepository.userRegistration(userRegistrationRequest)
            withContext(Dispatchers.Main) {
                _state.postValue(
                    if (response.isSuccessful) StateUser.Success(response.body())
                    else StateUser.Error("Error : ${response.message()} ")
                )
            }
        }
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