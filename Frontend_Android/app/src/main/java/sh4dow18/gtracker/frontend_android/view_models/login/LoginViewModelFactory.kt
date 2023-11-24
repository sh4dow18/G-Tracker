package sh4dow18.gtracker.frontend_android.view_models.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sh4dow18.gtracker.frontend_android.repositories.LoginRepository
import sh4dow18.gtracker.frontend_android.services.UserService

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            LoginViewModel(
                loginRepository = LoginRepository(
                    loginService = UserService.getInstance()
                )
            ) as T
        }
        else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}