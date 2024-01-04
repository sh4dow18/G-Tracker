package sh4dow18.gtracker.frontend_android.view_models.game

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
import sh4dow18.gtracker.frontend_android.repositories.GameRepository
import sh4dow18.gtracker.frontend_android.utils.GameResponse
import sh4dow18.gtracker.frontend_android.utils.getErrorMessage
import sh4dow18.gtracker.frontend_android.view_models.gameLog.StateGameLog


sealed class StateGame {
    data object Loading : StateGame()
    data class Success(val game: GameResponse?) : StateGame()
    data class SuccessList(val gamesList: List<GameResponse>?) : StateGame()
    data class Error(val message: String) : StateGame()
}

class GameViewModel constructor(
    private val gameRepository: GameRepository
) : AndroidViewModel(application = Application()) {
    private val _state = MutableLiveData<StateGame>()
    val state: LiveData<StateGame> get() = _state
    private var job: Job? = null
    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun findFirst20ByOrderByRatingDesc() {
        _state.value = StateGame.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = gameRepository.findFirst10ByOrderByRatingDesc()
            withContext(Dispatchers.Main) {
                _state.postValue(
                    if (response.isSuccessful) StateGame.SuccessList(response.body())
                    else StateGame.Error(getErrorMessage(response.errorBody()?.string()!!))
                )
            }
        }
    }

    fun findByNameContainingIgnoreCase(name: String) {
        _state.value = StateGame.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = gameRepository.findByNameContainingIgnoreCase(name)
            withContext(Dispatchers.Main) {
                _state.postValue(
                    if (response.isSuccessful) StateGame.SuccessList(response.body())
                    else StateGame.Error(getErrorMessage(response.errorBody()?.string()!!))
                )
            }
        }
    }

    fun findById(id: Long) {
        _state.value = StateGame.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = gameRepository.findById(id)
            withContext(Dispatchers.Main) {
                _state.postValue(
                    if (response.isSuccessful) StateGame.Success(response.body())
                    else StateGame.Error(getErrorMessage(response.errorBody()?.string()!!))
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