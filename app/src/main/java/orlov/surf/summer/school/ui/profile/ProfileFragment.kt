package orlov.surf.summer.school.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import orlov.surf.summer.school.R
import orlov.surf.summer.school.databinding.FragmentProfileBinding
import orlov.surf.summer.school.domain.model.User
import timber.log.Timber

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
        viewModel.profileState.postValue(ProfileUiStates.DEFAULT)
        setupObservers()
    }

    private fun setupObservers() {

       // binding.btnLogout.setOnClickListener { findNavController().navigate(R.id.action_profileFragment_to_loginFragment) }
        observeLoadState()

        viewModel.user.observe(viewLifecycleOwner) { user ->
            setupUI(user)
        }
    }

    private fun observeLoadState() {
        viewModel.profileState.observe(viewLifecycleOwner) { state ->
            when (state) {
                ProfileUiStates.LOADING -> {
                    Timber.d("LOADING LOADING")
                    binding.btnLogout.isLoading = true
                    binding.flBlockAction.visibility = View.VISIBLE
                }
                ProfileUiStates.ERROR -> {
                    Timber.d("ERROR ERROR")
                    binding.apply {
                        btnLogout.isLoading = false
                        flBlockAction.visibility = View.GONE
                        val snackbar = Snackbar.make(root, getString(R.string.logout_error),
                            Snackbar.LENGTH_LONG)
                        snackbar.anchorView = btnLogout
                        snackbar.show()
                    }
                    viewModel.profileState.postValue(ProfileUiStates.DEFAULT)
                }
                ProfileUiStates.LOGOUT -> {
                    Timber.d("LOGOUT LOGOUT")
                    binding.btnLogout.isLoading = false
                    binding.flBlockAction.visibility = View.GONE
                    viewModel.profileState.postValue(ProfileUiStates.DEFAULT)
                    findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                }
                ProfileUiStates.DEFAULT -> {
                    Timber.d("DEFAULT DEFAULT")
                    binding.btnLogout.isLoading = false
                    binding.flBlockAction.visibility = View.GONE
                }
            }
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