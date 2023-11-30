package sh4dow18.gtracker.frontend_android.view_models.gameLog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sh4dow18.gtracker.frontend_android.repositories.GameLogRepository
import sh4dow18.gtracker.frontend_android.services.GameLogService

@Suppress("UNCHECKED_CAST")
class GameLogViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GameLogViewModel::class.java)) {
            GameLogViewModel(
                gameLogRepository = GameLogRepository(
                    gameLogService = GameLogService.getInstance()
                )
            ) as T
        }
        else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}