package orlov.surf.summer.school.ui.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import orlov.surf.summer.school.R
import orlov.surf.summer.school.databinding.FragmentFavouritesPhotosBinding
import orlov.surf.summer.school.domain.model.Photo

class FavouritesPhotosFragment : Fragment(R.layout.fragment_favourites_photos) {

    private var _binding: FragmentFavouritesPhotosBinding? = null
    val binding
        get() = _binding!!

    private val adapter = PhotosAdapter(
        ViewHolderType.LARGE,
        object : PhotosAdapter.OnItemClickListener {
            override fun onPhotoClick(photo: Photo) {
                findNavController().navigate(R.id.action_favouritesFragment_to_photoInfoFragment)
            }

            override fun onLikeClick(photo: Photo) {
                photo.isLiked = !photo.isLiked
                viewModel.updatePhoto(photo)
            }
        }
    )

    private val viewModel: PhotosViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesPhotosBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        viewModel.savedPhotos.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.apply {
            rvPhotos.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}