package orlov.surf.summer.school.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.MaskedTextChangedListener.Companion.installOn
import dagger.hilt.android.AndroidEntryPoint
import orlov.surf.summer.school.R
import orlov.surf.summer.school.databinding.FragmentLoginBinding
import orlov.surf.summer.school.utils.LoadState
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by activityViewModels()

    private var _binding: FragmentLoginBinding? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        setupLoginMask()
        binding.apply {
            btnLogin.setOnClickListener {
                viewModel.auth()
            }
            edtPassword.doOnTextChanged { password, _, _, _ ->
                viewModel.setPassword(password.toString())
            }
        }
    }

    private fun setupObservers() {
        observeLoginError()
        observePasswordError()
        observeLoadState()
    }

    private fun observeLoginError() {
        viewModel.loginError.observe(viewLifecycleOwner) { loginError ->
            when (loginError) {
                LoginError.EMPTY -> binding.tilLogin.error = getString(R.string.empty_field)
                LoginError.NOT_VALID -> binding.tilLogin.error = getString(R.string.login_format)
                LoginError.VALID -> binding.tilLogin.error = null
                else -> {}
            }
        }
    }

    private fun observePasswordError() {
        viewModel.passwordError.observe(viewLifecycleOwner) { passwordError ->
            when (passwordError) {
                PasswordError.EMPTY -> binding.tilPassword.error = getString(R.string.empty_field)
                PasswordError.NOT_VALID -> binding.tilPassword.error =
                    getString(R.string.password_length)
                PasswordError.VALID -> binding.tilPassword.error = null
                else -> {}
            }
        }
    }

    private fun observeLoadState() {
        viewModel.loadState.observe(viewLifecycleOwner) { state ->
            when (state) {
                LoadState.LOADING -> setLoadingState()
                LoadState.ERROR -> setErrorState()
                LoadState.SUCCESS -> setSuccessState()
                LoadState.WAITING -> setWaitingState()
                else -> setWaitingState()
            }
        }
    }

    private fun setWaitingState() {
        binding.btnLogin.isLoading = false
        binding.flBlockAction.visibility = View.GONE
    }

    private fun setLoadingState() {
        binding.btnLogin.isLoading = true
        binding.flBlockAction.visibility = View.GONE
    }

    private fun setErrorState() {
        binding.btnLogin.isLoading = false
        binding.flBlockAction.isVisible = false
        showErrorSnackbar()
        setWaitingState()
    }

    private fun setSuccessState() {
        binding.btnLogin.isLoading = false
        binding.flBlockAction.isVisible = false
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        setWaitingState()
    }

    private fun showErrorSnackbar() {
        Snackbar
            .make(binding.root, getString(R.string.auth_error), Snackbar.LENGTH_LONG)
            .setAnchorView(binding.btnLogin)
            .show()
    }


    private fun setupLoginMask() {
        installOn(
            binding.edtLogin,
            PHONE_MASK,
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                    viewModel.setLogin(extractedValue)
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val PHONE_MASK = "+7 ([000]) [000] [00] [00]"
    }

}