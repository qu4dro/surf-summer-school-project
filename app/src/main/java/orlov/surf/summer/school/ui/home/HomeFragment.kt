package orlov.surf.summer.school.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import orlov.surf.summer.school.R
import orlov.surf.summer.school.databinding.FragmentHomeBinding
import orlov.surf.summer.school.ui.PhotosAdapter
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
            adapter.submitList(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}