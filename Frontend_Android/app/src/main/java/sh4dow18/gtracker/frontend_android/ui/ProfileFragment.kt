package sh4dow18.gtracker.frontend_android.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import sh4dow18.gtracker.frontend_android.databinding.FragmentProfileBinding
import sh4dow18.gtracker.frontend_android.utils.MyApplication
import sh4dow18.gtracker.frontend_android.view_models.user.StateUser
import sh4dow18.gtracker.frontend_android.view_models.user.UserViewModel
import sh4dow18.gtracker.frontend_android.view_models.user.UserViewModelFactory

class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding ?= null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    companion object {
        fun newInstance() = ProfileFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(this, UserViewModelFactory())[UserViewModel::class.java]
        userViewModel.findUserById(MyApplication.sessionManager!!.getUserEmail())
        userViewModel.state.observe(viewLifecycleOwner){ state ->
            when (state) {
                StateUser.Loading -> {}
                is StateUser.Error -> {}
                is StateUser.Success -> {
                    val user = state.user!!
                    binding.UserNameLabel.text = user.userName
                    binding.EmailValue.text = user.email
                    binding.RoleValue.text = user.role.name
                }
                else -> {}
            }
        }
        return binding.root
    }
}