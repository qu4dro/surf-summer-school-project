package orlov.surf.summer.school.ui.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import orlov.surf.summer.school.R
import orlov.surf.summer.school.databinding.FragmentPhotoInfoBinding
import orlov.surf.summer.school.domain.model.Photo
import orlov.surf.summer.school.utils.formatDate

@AndroidEntryPoint
class PhotoInfoFragment : Fragment(R.layout.fragment_photo_info) {

    private var _binding: FragmentPhotoInfoBinding? = null
    val binding
        get() = _binding!!

    private val viewModel: PhotosViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedPhoto.observe(viewLifecycleOwner) {
            setupPhotoUi(it)
        }
        binding.btnBack.setOnClickListener { findNavController().navigateUp() }
    }

    private fun setupPhotoUi(photo: Photo) {
        binding.apply {
            tvTitlePhoto.text = photo.title
            tvDate.text = photo.publicationDate.formatDate()
            ivPhoto.load(photo.photoUrl)
            tvDescription.text = photo.content
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}