package sh4dow18.gtracker.frontend_android.view_models.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sh4dow18.gtracker.frontend_android.repositories.UserRepository
import sh4dow18.gtracker.frontend_android.services.UserService

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            UserViewModel(
                userRepository = UserRepository(
                    userService = UserService.getInstance()
                )
            ) as T
        }
        else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}