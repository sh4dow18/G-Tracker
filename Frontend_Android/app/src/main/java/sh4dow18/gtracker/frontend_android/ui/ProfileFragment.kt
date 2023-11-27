package sh4dow18.gtracker.frontend_android.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import sh4dow18.gtracker.frontend_android.MainActivity
import sh4dow18.gtracker.frontend_android.R
import sh4dow18.gtracker.frontend_android.databinding.FragmentProfileBinding
import sh4dow18.gtracker.frontend_android.utils.MyApplication
import sh4dow18.gtracker.frontend_android.utils.UpdateUserRequest
import sh4dow18.gtracker.frontend_android.view_models.login.LoginViewModel
import sh4dow18.gtracker.frontend_android.view_models.login.LoginViewModelFactory
import sh4dow18.gtracker.frontend_android.view_models.user.StateUser
import sh4dow18.gtracker.frontend_android.view_models.user.UserViewModel
import sh4dow18.gtracker.frontend_android.view_models.user.UserViewModelFactory

class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding ?= null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    private lateinit var loginViewModel: LoginViewModel
    companion object {
        fun newInstance() = ProfileFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]
        userViewModel = ViewModelProvider(this, UserViewModelFactory())[UserViewModel::class.java]
        userViewModel.findUserById(MyApplication.sessionManager!!.getUserEmail())
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
                    val user = state.user!!
                    binding.UserNameLabel.text = user.userName
                    binding.EmailValue.text = user.email
                    binding.RoleValue.text = user.role.name
                    if (user.imagePath != null) {
                        Glide.with(this)
                            .load("http://192.168.0.13:8080/api/public/image/user/" +
                                    user.imagePath)
                            .into(binding.ProfileImage)
                    }
                }
                else -> {}
            }
        }

        binding.UpdateProfile.setOnClickListener {
            findNavController().navigate(R.id.nav_update_profile)
        }
        binding.LogOut.setOnClickListener {
            loginViewModel.logout()
            activity?.finish()
            startActivity(Intent(activity, MainActivity::class.java))
        }
        binding.CloseAccount.setOnClickListener {
            userViewModel.closeAccount(MyApplication.sessionManager!!.getUserEmail())
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
                        loginViewModel.logout()
                        activity?.finish()
                        startActivity(Intent(activity, MainActivity::class.java))
                        Toast.makeText(
                            requireContext(),
                            "Eliminación de Cuenta Exitosa",
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