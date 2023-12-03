package sh4dow18.gtracker.frontend_android.ui

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.net.toFile
import androidx.core.widget.doAfterTextChanged
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import sh4dow18.gtracker.frontend_android.R
import sh4dow18.gtracker.frontend_android.databinding.FragmentUpdateProfileBinding
import sh4dow18.gtracker.frontend_android.utils.MyApplication
import sh4dow18.gtracker.frontend_android.utils.UpdateUserRequest
import sh4dow18.gtracker.frontend_android.view_models.user.StateUser
import sh4dow18.gtracker.frontend_android.view_models.user.UserViewModel
import sh4dow18.gtracker.frontend_android.view_models.user.UserViewModelFactory
import java.io.File
import java.io.IOException
import java.io.InputStream


class UpdateProfileFragment : Fragment() {
    private var _binding : FragmentUpdateProfileBinding ?= null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    private var selectedImageUri: Uri? = null
    private var selectedImagePath: String? = null
    private var selectedFile: File? = null
    private lateinit var resultLauncher: ActivityResultLauncher<String>
    companion object {
        fun newInstance() = UpdateProfileFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateProfileBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(this, UserViewModelFactory())[UserViewModel::class.java]
        resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                binding.ImageSelectButton.text = "Image Selected"
            }
        }
        binding.ImageSelectButton.setOnClickListener {
            resultLauncher.launch("image/*")
        }
        binding.Submit.setOnClickListener {
            var fileToSend: File? = null
            if (selectedImageUri != null) {
                try {
                    val inputStream: InputStream? = requireContext().contentResolver.openInputStream(
                        selectedImageUri!!
                    )
                    if (inputStream != null) {
                        val file = File.createTempFile("image_", ".jpg", requireContext().cacheDir)
                        file.deleteOnExit()
                        file.outputStream().use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                        fileToSend = file
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            userViewModel.updateUser(fileToSend, UpdateUserRequest(
                MyApplication.sessionManager!!.getUserEmail(),
                binding.UserNameInput.text.toString())
            )
            userViewModel.state.observe(viewLifecycleOwner){ state ->
                when (state) {
                    StateUser.Loading -> {}
                    is StateUser.Error -> {
                        Toast.makeText(
                            requireContext(),
                            state.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is StateUser.Success -> {
                        findNavController().navigate(R.id.nav_profile)
                        Toast.makeText(
                            requireContext(),
                            "Successful Update",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> {}
                }
            }
        }
        return binding.root
    }
}