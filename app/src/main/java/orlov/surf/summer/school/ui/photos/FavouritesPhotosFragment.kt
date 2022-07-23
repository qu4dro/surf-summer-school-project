package orlov.surf.summer.school.ui.photos

import android.app.AlertDialog
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
                if(photo.isLiked) {
                    showUnlikeDialog(photo)
                } else {
                    viewModel.likePhoto(photo)
                }
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
            if(it.isNullOrEmpty()) {
                binding.groupError.visibility = View.VISIBLE
                binding.rvPhotos.visibility = View.GONE
            } else {
                binding.groupError.visibility = View.GONE
                binding.rvPhotos.visibility = View.VISIBLE
            }
        }
        binding.apply {
            rvPhotos.adapter = adapter
        }
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