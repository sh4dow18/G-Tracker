package sh4dow18.gtracker.frontend_android.view_models.user

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import sh4dow18.gtracker.frontend_android.repositories.UserRepository
import sh4dow18.gtracker.frontend_android.utils.UpdateUserRequest
import sh4dow18.gtracker.frontend_android.utils.UserRegistrationRequest
import sh4dow18.gtracker.frontend_android.utils.UserResponse
import java.io.File
import java.io.IOException


sealed class StateUser {
    data object Loading : StateUser()
    data class Success(val user: UserResponse?) : StateUser()
    data class Error(val message: String) : StateUser()
}

class UserViewModel constructor(
    private val userRepository: UserRepository
) : AndroidViewModel(application = Application()) {
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
            try {
                val response = userRepository.findUserById(email)
                withContext(Dispatchers.Main) {
                    _state.postValue(
                        if (response.isSuccessful) StateUser.Success(response.body())
                        else StateUser.Error("Error : ${response.message()} ")
                    )
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _state.postValue(StateUser.Error("Servers Unavailable. Try Again later"))
                }
            }
        }
    }

    fun userRegistration(userRegistrationRequest: UserRegistrationRequest) {
        _state.value = StateUser.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            try {
                val response = userRepository.userRegistration(userRegistrationRequest)
                withContext(Dispatchers.Main) {
                    _state.postValue(
                        if (response.isSuccessful) StateUser.Success(response.body())
                        else StateUser.Error("Error : ${response.message()} ")
                    )
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _state.postValue(StateUser.Error("Servers Unavailable. Try Again later"))
                }
            }
        }
    }

    fun updateUser(image: Uri, updateUserRequest: UpdateUserRequest) {
        _state.value = StateUser.Loading
        val contentResolver = getApplication<Application>().contentResolver
        val file = File(getRealPathFromURI(contentResolver, image))
        val requestFile = file.asRequestBody("multipart/form-data".toMediaType())
        val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)
        val gson = Gson()
        val userJson = gson.toJson(updateUserRequest)
        val userRequestBody = userJson.toRequestBody("application/json".toMediaType())
        val userPart = MultipartBody.Part.createFormData("user", null, userRequestBody)
        val formData = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addPart(imagePart)
            .addPart(userPart)
            .build()
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            try {
                val response = userRepository.updateUser(formData)
                withContext(Dispatchers.Main) {
                    _state.postValue(
                        if (response.isSuccessful) StateUser.Success(response.body())
                        else StateUser.Error("Error : ${response.message()} ")
                    )
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _state.postValue(StateUser.Error("Servers Unavailable. Try Again later"))
                }
            }
        }
    }

    fun closeAccount(id: String) {
        _state.value = StateUser.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            try {
                val response = userRepository.closeAccount(id)
                withContext(Dispatchers.Main) {
                    _state.postValue(
                        if (response.isSuccessful) StateUser.Success(response.body())
                        else StateUser.Error("Error : ${response.message()} ")
                    )
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _state.postValue(StateUser.Error("Servers Unavailable. Try Again later"))
                }
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
    fun isValidImage(uri: Uri): Boolean {
        return try {
            val type: String? = getApplication<Application>().contentResolver.getType(uri)
            type != null && (type == "image/jpeg" || type == "image/png")
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private fun getRealPathFromURI(contentResolver: ContentResolver, contentUri: Uri): String {
        val cursor = contentResolver.query(contentUri, null, null, null, null)
        cursor.use {
            it?.moveToFirst()
            val columnIndex = it?.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            return it?.getString(columnIndex!!) ?: ""
        }
    }
}