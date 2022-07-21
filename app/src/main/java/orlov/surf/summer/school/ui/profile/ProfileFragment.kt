package orlov.surf.summer.school.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import orlov.surf.summer.school.R
import orlov.surf.summer.school.databinding.FragmentProfileBinding
import orlov.surf.summer.school.domain.model.User
import orlov.surf.summer.school.utils.LoadState

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by activityViewModels()

    private var _binding: FragmentProfileBinding? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {

        observeLoadState()

        viewModel.user.observe(viewLifecycleOwner) { user ->
            setupUI(user)
        }
    }

    private fun observeLoadState() {
        viewModel.loadState.observe(viewLifecycleOwner) { state ->
            when (state) {
                LoadState.LOADING -> {
                    setLoadingUIState()
                }
                LoadState.ERROR -> {
                    setErrorUIState()
                }
                LoadState.SUCCESS -> {
                    setSuccessUIState()
                }
            }
        }
    }

    private fun setSuccessUIState() {
        binding.apply {
            btnLogout.isLoading = false
            flBlockAction.isVisible = false
        }
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
    }

    private fun setErrorUIState() {
        binding.apply {
            btnLogout.isLoading = false
            flBlockAction.isVisible = false
            val snackbar = Snackbar.make(binding.root, getString(R.string.auth_error), Snackbar.LENGTH_LONG)
            snackbar.anchorView = btnLogout
            snackbar.show()
        }

    }

    private fun setLoadingUIState() {
        binding.apply {
            btnLogout.isLoading = true
            flBlockAction.isVisible = true
        }
    }

    private fun setupUI(user: User) {

        binding.btnLogout.setOnClickListener { viewModel.logout(user.token) }

        val firstName = user.userInfo.firstName
        val lastName = user.userInfo.lastName
        val city = user.userInfo.city
        val about = user.userInfo.about
        val phone = user.userInfo.phone
        val email = user.userInfo.email
        val avatarURL = user.userInfo.avatar

        binding.ivProfilePic.load(avatarURL)

        binding.tvName.apply {
            if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                this.visibility = View.VISIBLE
                this.text = "$firstName $lastName"
            } else {
                this.visibility = View.GONE
            }
        }

        binding.tvAbout.apply {
            if (about.isNotEmpty()) {
                this.visibility = View.VISIBLE
                this.text = about
            } else {
                this.visibility = View.GONE
            }
        }

        binding.tvCity.apply {
            setTextOrChangeGroupVisibility(this, binding.groupCity, city)
        }

        binding.tvPhone.apply {
            setTextOrChangeGroupVisibility(this, binding.groupPhone, phone.formatToPhone())
        }

        binding.tvEmail.apply {
            setTextOrChangeGroupVisibility(this, binding.groupEmail, email)
        }

    }

    private fun setTextOrChangeGroupVisibility(
        textView: MaterialTextView,
        group: Group,
        text: String
    ) {
        if (text.isNotEmpty()) {
            textView.text = text
            group.visibility = View.VISIBLE
        } else {
            group.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}