package sh4dow18.gtracker.frontend_android.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import sh4dow18.gtracker.frontend_android.R
import sh4dow18.gtracker.frontend_android.databinding.FragmentProfileBinding
import sh4dow18.gtracker.frontend_android.databinding.FragmentSignUpBinding
import sh4dow18.gtracker.frontend_android.utils.MyApplication
import sh4dow18.gtracker.frontend_android.utils.UserRegistrationRequest
import sh4dow18.gtracker.frontend_android.view_models.user.StateUser
import sh4dow18.gtracker.frontend_android.view_models.user.UserViewModel
import sh4dow18.gtracker.frontend_android.view_models.user.UserViewModelFactory

class SignUpFragment : Fragment() {
    private var _binding : FragmentSignUpBinding ?= null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel

    companion object {
        fun newInstance() = SignUpFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(this, UserViewModelFactory())[UserViewModel::class.java]
        fun validForm() {
            val emailValid = binding.EmailInput.text.matches(Regex("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\$"))
            if (!emailValid) {
                binding.EmailInput.error = "Invalid Email"
            }
            val userNameValid = binding.UserNameInput.length() <= 20
            if (!userNameValid) {
                binding.UserNameInput.error = "Invalid User Name"
            }
            val passwordValid = binding.PasswordInput.text.matches(Regex("^[^\\\\'\"=;%<>&?+]+\$")) && binding.PasswordInput.length() >= 10
            if (!passwordValid) {
                binding.PasswordInput.error = "Invalid Password"
            }
            val confirmPasswordValid = binding.PasswordInput.text.toString() == binding.ConfirmPasswordInput.text.toString()
            if (!confirmPasswordValid) {
                binding.ConfirmPasswordInput.error = "Different Password"
            }
            binding.Submit.isEnabled = emailValid && userNameValid && passwordValid && confirmPasswordValid
        }
        binding.EmailInput.doAfterTextChanged {
            validForm()
        }
        binding.UserNameInput.doAfterTextChanged {
            validForm()
        }
        binding.PasswordInput.doAfterTextChanged {
            validForm()
        }
        binding.ConfirmPasswordInput.doAfterTextChanged {
            validForm()
        }
        binding.Submit.setOnClickListener {
            userViewModel.userRegistration(UserRegistrationRequest(
                email = binding.EmailInput.text.toString(),
                userName = binding.UserNameInput.text.toString(),
                password = binding.PasswordInput.text.toString()))
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
                        findNavController().navigate(R.id.nav_sign_in)
                        Toast.makeText(
                            requireContext(),
                            "Registro Existoso, favor inicie sesión",
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