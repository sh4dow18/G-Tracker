package sh4dow18.gtracker.frontend_android.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import sh4dow18.gtracker.frontend_android.MainActivity
import sh4dow18.gtracker.frontend_android.databinding.FragmentLastWarningCloseAccountBinding
import sh4dow18.gtracker.frontend_android.utils.MyApplication
import sh4dow18.gtracker.frontend_android.view_models.login.LoginViewModel
import sh4dow18.gtracker.frontend_android.view_models.login.LoginViewModelFactory
import sh4dow18.gtracker.frontend_android.view_models.user.StateUser
import sh4dow18.gtracker.frontend_android.view_models.user.UserViewModel
import sh4dow18.gtracker.frontend_android.view_models.user.UserViewModelFactory

class LastWarningCloseAccountFragment : Fragment() {
    private var _binding: FragmentLastWarningCloseAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLastWarningCloseAccountBinding.inflate(inflater, container, false)
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]
        userViewModel = ViewModelProvider(this, UserViewModelFactory())[UserViewModel::class.java]
        binding.DeleteAccountSubmit.setOnClickListener {
            userViewModel.closeAccount(MyApplication.sessionManager!!.getUserEmail())
            userViewModel.state.observe(viewLifecycleOwner){ state ->
                when (state) {
                    StateUser.Loading -> {
                        binding.FragmentLoading.visibility = View.VISIBLE
                    }
                    is StateUser.Error -> {
                        Toast.makeText(
                            requireContext(),
                            state.message,
                            Toast.LENGTH_LONG
                        ).show()
                        binding.FragmentLoading.visibility = View.GONE
                    }
                    is StateUser.Success -> {
                        binding.FragmentLoading.visibility = View.GONE
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

    companion object {
        fun newInstance() = LastWarningCloseAccountFragment()
    }
}