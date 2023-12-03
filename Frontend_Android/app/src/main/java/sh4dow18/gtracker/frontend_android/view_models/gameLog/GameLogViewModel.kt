package sh4dow18.gtracker.frontend_android.view_models.gameLog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sh4dow18.gtracker.frontend_android.repositories.GameLogRepository
import sh4dow18.gtracker.frontend_android.utils.GameLogRegistrationRequest
import sh4dow18.gtracker.frontend_android.utils.GameLogResponse
import sh4dow18.gtracker.frontend_android.utils.GameLogUpdateRequest
import sh4dow18.gtracker.frontend_android.view_models.game.StateGame


sealed class StateGameLog {
    data object Loading : StateGameLog()
    data class Success(val gameLog: GameLogResponse?) : StateGameLog()
    data class SuccessList(val gameLogsList: List<GameLogResponse>?) : StateGameLog()
    data class Error(val message: String) : StateGameLog()
}

class GameLogViewModel constructor(
    private val gameLogRepository: GameLogRepository
) : AndroidViewModel(application = Application()) {
    private val _state = MutableLiveData<StateGameLog>()
    val state: LiveData<StateGameLog> get() = _state
    private var job: Job? = null
    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun findAllByUserEmail(email: String) {
        _state.value = StateGameLog.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = gameLogRepository.findAllByUserEmail(email)
            withContext(Dispatchers.Main) {
                _state.postValue(
                    if (response.isSuccessful) StateGameLog.SuccessList(response.body())
                    else {
                        if (response.code() == 403) {
                            StateGameLog.Error("Your Session Expired, please sign in again")
                        }
                        else {
                            StateGameLog.Error("Server Unavailable")
                        }
                    }
                )
            }
        }
    }

    fun findGameLogById(id: Long) {
        _state.value = StateGameLog.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = gameLogRepository.findGameLogById(id)
            withContext(Dispatchers.Main) {
                _state.postValue(
                    if (response.isSuccessful) StateGameLog.Success(response.body())
                    else {
                        if (response.code() == 403) {
                            StateGameLog.Error("Your Session Expired, please sign in again")
                        }
                        else {
                            StateGameLog.Error("Server Unavailable")
                        }
                    }
                )
            }
        }
    }

    fun gameLogRegistration(gameLogRegistrationRequest: GameLogRegistrationRequest) {
        _state.value = StateGameLog.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = gameLogRepository.gameLogRegistration(gameLogRegistrationRequest)
            withContext(Dispatchers.Main) {
                _state.postValue(
                    if (response.isSuccessful) StateGameLog.Success(response.body())
                    else {
                        if (response.code() == 403) {
                            StateGameLog.Error("Your Session Expired, please sign in again")
                        }
                        else {
                            StateGameLog.Error("Server Unavailable")
                        }
                    }
                )
            }
        }
    }

    fun gameLogUpdateFinished(id: Long) {
        _state.value = StateGameLog.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = gameLogRepository.gameLogUpdateFinished(id)
            withContext(Dispatchers.Main) {
                _state.postValue(
                    if (response.isSuccessful) StateGameLog.Success(response.body())
                    else {
                        if (response.code() == 403) {
                            StateGameLog.Error("Your Session Expired, please sign in again")
                        }
                        else {
                            StateGameLog.Error("Server Unavailable")
                        }
                    }
                )
            }
        }
    }

    fun gameLogUpdateFinishedAtAll(id: Long) {
        _state.value = StateGameLog.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = gameLogRepository.gameLogUpdateFinishedAtAll(id)
            withContext(Dispatchers.Main) {
                _state.postValue(
                    if (response.isSuccessful) StateGameLog.Success(response.body())
                    else {
                        if (response.code() == 403) {
                            StateGameLog.Error("Your Session Expired, please sign in again")
                        }
                        else {
                            StateGameLog.Error("Server Unavailable")
                        }
                    }
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