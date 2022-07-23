package orlov.surf.summer.school.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import orlov.surf.summer.school.R
import orlov.surf.summer.school.databinding.FragmentHomeBinding
import orlov.surf.summer.school.ui.PhotosAdapter
import orlov.surf.summer.school.utils.LoadState
import orlov.surf.summer.school.utils.PhotoLoadState
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by activityViewModels()

    private val adapter = PhotosAdapter {
        Timber.d(it.toString())
    }

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


        binding.rvPhotos.adapter = adapter

        viewModel.photos.observe(viewLifecycleOwner) {
            Timber.d(it.toString())
            adapter.submitList(it)

        }
        viewModel.fetchPhotos()

        setupUI()
        //observeLoadState()
    }

    private fun setupUI() {

        binding.apply {
            btnRefresh.setOnClickListener {
                viewModel.fetchPhotos()
            }

            srlRefresh.setOnRefreshListener {
                viewModel.fetchPhotos()
            }
        }
    }

    private fun observeLoadState() {
        viewModel.loadState.observe(viewLifecycleOwner) { state ->
            when (state) {
                LoadState.LOADING -> setLoadingState()
                LoadState.SUCCESS -> setSuccessState()
                LoadState.ERROR -> setErrorState()
                LoadState.WAITING -> setWaitingState()
                else -> setWaitingState()
            }
        }
    }

    private fun setWaitingState() {
        binding.apply {
        }
    }

    private fun setLoadingState() {
        binding.apply {
            if (viewModel.isFirstLoading || viewModel.photos.value.isNullOrEmpty()) {
                pbLoading.visibility = View.VISIBLE
                rvPhotos.visibility = View.GONE
                btnSearch.visibility = View.GONE
                btnRefresh.visibility = View.GONE
                groupError.visibility = View.GONE
                srlRefresh.isRefreshing = false
                srlRefresh.visibility = View.GONE
            } else {
                pbLoading.visibility = View.GONE
                rvPhotos.visibility = View.VISIBLE
                btnSearch.visibility = View.VISIBLE
                btnRefresh.visibility = View.GONE
                groupError.visibility = View.GONE
                srlRefresh.isRefreshing = true
                srlRefresh.visibility = View.VISIBLE
            }
        }
    }

    private fun setErrorState() {
        binding.apply {
            if (viewModel.isFirstLoading || viewModel.photos.value.isNullOrEmpty()) {
                pbLoading.visibility = View.GONE
                groupError.visibility = View.VISIBLE
                btnRefresh.visibility = View.VISIBLE
                rvPhotos.visibility = View.GONE
                btnSearch.visibility = View.GONE
            } else {
                pbLoading.visibility = View.GONE
                groupError.visibility = View.GONE
                btnRefresh.visibility = View.GONE
                rvPhotos.visibility = View.VISIBLE
                btnSearch.visibility = View.VISIBLE
                showConnectionErrorSnackbar()
            }
        }
    }

    private fun setSuccessState() {
        binding.apply {

        }
        //setWaitingState()
    }

    private fun showConnectionErrorSnackbar() {
        Snackbar
            .make(binding.root, R.string.connection_error, Snackbar.LENGTH_LONG)
            .setAnchorView(binding.btnRefresh)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}