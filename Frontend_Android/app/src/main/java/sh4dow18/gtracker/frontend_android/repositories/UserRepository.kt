package sh4dow18.gtracker.frontend_android.repositories

import okhttp3.MultipartBody
import sh4dow18.gtracker.frontend_android.services.UserService
import sh4dow18.gtracker.frontend_android.utils.UserRegistrationRequest

class UserRepository(
    private val userService: UserService
) {
    suspend fun findUserById(id: String) = userService.findUserById(id)

    suspend fun userRegistration(userRegistrationRequest: UserRegistrationRequest) = userService.userRegistration(userRegistrationRequest)

    suspend fun updateUser(formData: MultipartBody) = userService.updateUser(formData)

    suspend fun closeAccount(id: String) = userService.closeAccount(id)
}