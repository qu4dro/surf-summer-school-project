package orlov.surf.summer.school.ui.photos

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import orlov.surf.summer.school.R
import orlov.surf.summer.school.databinding.FragmentHomeBinding
import orlov.surf.summer.school.domain.model.Photo
import orlov.surf.summer.school.utils.LoadState
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: PhotosViewModel by activityViewModels()

    private val adapter = PhotosAdapter(
        ViewHolderType.SMALL,
        object : PhotosAdapter.OnItemClickListener {
            override fun onPhotoClick(photo: Photo) {
                viewModel.setSelectedPhoto(photo)
                findNavController().navigate(R.id.action_homeFragment_to_photoInfoFragment)
            }
            override fun onLikeClick(photo: Photo) {
                if (photo.isLiked) {
                    showUnlikeDialog(photo)
                } else {
                    viewModel.likePhoto(photo)
                }
            }
        }
    )

    private var _binding: FragmentHomeBinding? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchPhotos()
        setupUI()
        observeLoadState()
    }

    private fun setupUI() {
        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.apply {
            rvPhotos.adapter = adapter
            btnRefresh.setOnClickListener { viewModel.fetchPhotos() }
            srlRefresh.setOnRefreshListener { viewModel.fetchPhotos() }
        }
    }

    private fun observeLoadState() {
        viewModel.loadState.observe(viewLifecycleOwner) { state ->
            Timber.d(state.toString())
            val isListEmpty = viewModel.photos.value.isNullOrEmpty()
            Timber.d(isListEmpty.toString())
            when (state) {
                LoadState.LOADING -> {
                    if (isListEmpty) {
                        setLoadingStateEmpty()
                    } else {
                        setLoadingStateNotEmpty()
                    }
                }
                LoadState.SUCCESS -> {
                    if (isListEmpty) {
                        setSuccessStateEmpty()
                    } else {
                        setSuccessStateNotEmpty()
                    }
                }
                LoadState.ERROR -> {
                    if (isListEmpty) {
                        setErrorStateEmpty()
                    } else {
                        setErrorStateNotEmpty()
                    }
                }
                LoadState.WAITING -> setWaitingState()
                else -> Timber.d("12313123121212")
            }
        }
    }

    private fun setLoadingStateEmpty() {
        binding.apply {
            rvPhotos.visibility = View.GONE
            btnSearch.visibility = View.GONE
            btnRefresh.visibility = View.GONE
            groupError.visibility = View.GONE
            srlRefresh.visibility = View.GONE
            srlRefresh.isRefreshing = false
            pbLoading.visibility = View.VISIBLE
        }
    }

    private fun setErrorStateEmpty() {
        binding.apply {
            rvPhotos.visibility = View.GONE
            btnSearch.visibility = View.GONE
            btnRefresh.visibility = View.VISIBLE
            groupError.visibility = View.VISIBLE
            srlRefresh.visibility = View.GONE
            srlRefresh.isRefreshing = false
            pbLoading.visibility = View.GONE
        }
        showConnectionErrorSnackbar()
    }

    private fun setSuccessStateEmpty() {
        binding.apply {
            rvPhotos.visibility = View.GONE
            btnSearch.visibility = View.GONE
            btnRefresh.visibility = View.VISIBLE
            groupError.visibility = View.VISIBLE
            srlRefresh.visibility = View.GONE
            srlRefresh.isRefreshing = false
            pbLoading.visibility = View.GONE
        }
    }

    private fun setLoadingStateNotEmpty() {
        binding.apply {
            rvPhotos.visibility = View.VISIBLE
            btnSearch.visibility = View.VISIBLE
            btnRefresh.visibility = View.GONE
            groupError.visibility = View.GONE
            srlRefresh.visibility = View.VISIBLE
            srlRefresh.isRefreshing = true
            pbLoading.visibility = View.GONE
        }
    }

    private fun setErrorStateNotEmpty() {
        binding.apply {
            rvPhotos.visibility = View.VISIBLE
            btnSearch.visibility = View.VISIBLE
            btnRefresh.visibility = View.GONE
            groupError.visibility = View.GONE
            srlRefresh.visibility = View.VISIBLE
            srlRefresh.isRefreshing = false
            pbLoading.visibility = View.GONE
        }
        showConnectionErrorSnackbar()
    }

    private fun setSuccessStateNotEmpty() {
        binding.apply {
            rvPhotos.visibility = View.VISIBLE
            btnSearch.visibility = View.VISIBLE
            btnRefresh.visibility = View.GONE
            groupError.visibility = View.GONE
            srlRefresh.visibility = View.VISIBLE
            srlRefresh.isRefreshing = false
            pbLoading.visibility = View.GONE
        }
    }

    private fun setWaitingState() {
        binding.apply {
        }
    }

    private fun showConnectionErrorSnackbar() {
        Snackbar
            .make(binding.root, R.string.connection_error, Snackbar.LENGTH_LONG)
            .setAnchorView(requireActivity().findViewById(R.id.nv_bottom_navigation))
            .show()
    }

    private fun showUnlikeDialog(photo: Photo) {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.unlike_alert)
            .setPositiveButton(R.string.alert_yes) { _, _ -> viewModel.likePhoto(photo)}
            .setNegativeButton(R.string.alert_no, null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}