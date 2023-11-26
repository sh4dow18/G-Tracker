package sh4dow18.gtracker.frontend_android.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import sh4dow18.gtracker.frontend_android.R
import sh4dow18.gtracker.frontend_android.databinding.FragmentUpdateProfileBinding
import sh4dow18.gtracker.frontend_android.utils.MyApplication
import sh4dow18.gtracker.frontend_android.utils.UpdateUserRequest
import sh4dow18.gtracker.frontend_android.view_models.user.StateUser
import sh4dow18.gtracker.frontend_android.view_models.user.UserViewModel
import sh4dow18.gtracker.frontend_android.view_models.user.UserViewModelFactory


class UpdateProfileFragment : Fragment() {
    private var _binding : FragmentUpdateProfileBinding ?= null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    companion object {
        fun newInstance() = UpdateProfileFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateProfileBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(this, UserViewModelFactory())[UserViewModel::class.java]
        var selectedImage: Uri? = null
        fun validForm() {
            val validUserName = binding.UserNameInput.length() <= 20
            if (!validUserName) {
                binding.UserNameInput.error = "Invalid User Name"
            }
            binding.Submit.isEnabled = selectedImage != null && validUserName
        }
        val galleryLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data != null && data.data != null) {
                    val uri = data.data
                    if (userViewModel.isValidImage(uri!!)) {
                        selectedImage = uri
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "La imagen seleccionada no es válida.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            validForm()
        }
        binding.ImageSelectButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryLauncher.launch(intent)
        }
        binding.UserNameInput.doAfterTextChanged {
            validForm()
        }
        binding.Submit.setOnClickListener {
            userViewModel.updateUser(selectedImage!!, UpdateUserRequest(
                MyApplication.sessionManager!!.getUserEmail(),
                binding.UserNameInput.text.toString()))
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
                            "Actualización Exitosa",
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