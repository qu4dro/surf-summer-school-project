package orlov.surf.summer.school.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import orlov.surf.summer.school.R
import orlov.surf.summer.school.databinding.FragmentSplashBinding
import orlov.surf.summer.school.utils.AuthState


@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by activityViewModels()

    private var _binding: FragmentSplashBinding? = null
    val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAuthState()
    }

    private fun observeAuthState() {
        viewModel.authState.observe(viewLifecycleOwner) { authState ->
            when (authState) {
                AuthState.UNAUTHORIZED -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                    }, LOGO_DELAY)
                }
                AuthState.AUTHORIZED -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                    }, LOGO_DELAY)
                }
                else -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                    }, LOGO_DELAY)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val LOGO_DELAY = 1000L
    }
}