package sh4dow18.gtracker.frontend_android.view_models.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sh4dow18.gtracker.frontend_android.repositories.GameRepository
import sh4dow18.gtracker.frontend_android.repositories.UserRepository
import sh4dow18.gtracker.frontend_android.services.GameService
import sh4dow18.gtracker.frontend_android.services.UserService
import sh4dow18.gtracker.frontend_android.view_models.user.UserViewModel

@Suppress("UNCHECKED_CAST")
class GameViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            GameViewModel(
                gameRepository = GameRepository(
                    gameService = GameService.getInstance()
                )
            ) as T
        }
        else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}