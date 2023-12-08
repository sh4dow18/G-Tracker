package sh4dow18.gtracker.frontend_android.ui

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import sh4dow18.gtracker.frontend_android.R
import sh4dow18.gtracker.frontend_android.TrackerActivity
import sh4dow18.gtracker.frontend_android.databinding.FragmentSignInBinding
import sh4dow18.gtracker.frontend_android.utils.LoggedInUserView
import sh4dow18.gtracker.frontend_android.utils.MyApplication
import sh4dow18.gtracker.frontend_android.utils.UserLoginRequest
import sh4dow18.gtracker.frontend_android.view_models.login.LoginViewModel
import sh4dow18.gtracker.frontend_android.view_models.login.LoginViewModelFactory

class SignInFragment : Fragment() {
    companion object {
        fun newInstance() = SignInFragment()
    }
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel =
            ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]

        loginViewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer
            binding.Submit.isEnabled = loginState.isDataValid
            if (loginState.usernameError != null) {
                binding.EmailInput.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                binding.PasswordInput.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer
            binding.FragmentLoading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                MyApplication.sessionManager?.setUserEmail(loginResult.success.username)
                updateUiWithUser(loginResult.success)
            }
            // setResult(Activity.RESULT_OK)
        })

        binding.EmailInput.doAfterTextChanged {
            loginViewModel.loginDataChanged(
                UserLoginRequest(
                    email = binding.EmailInput.text.toString(),
                    password = binding.PasswordInput.text.toString()
                )
            )
        }

        binding.PasswordInput.apply {
            doAfterTextChanged {
                loginViewModel.loginDataChanged(
                    UserLoginRequest(
                        email = binding.EmailInput.text.toString(),
                        password = binding.PasswordInput.text.toString()
                    )
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> loginViewModel.login(
                        UserLoginRequest(
                            email = binding.EmailInput.text.toString(),
                            password = binding.PasswordInput.text.toString()
                        )
                    )
                }
                false
            }

            binding.Submit.setOnClickListener {
                binding.FragmentLoading.visibility = View.VISIBLE
                loginViewModel.login(
                    UserLoginRequest(
                        email = binding.EmailInput.text.toString(),
                        password = binding.PasswordInput.text.toString()
                    )
                )
            }
        }
    }
    private fun updateUiWithUser(model: LoggedInUserView) {
        activity?.finish()
        val intent = Intent(activity, TrackerActivity::class.java)
        startActivity(intent)
        Toast.makeText(
            requireContext(),
            getString(R.string.welcome),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(
            requireContext(),
            getString(errorString),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}