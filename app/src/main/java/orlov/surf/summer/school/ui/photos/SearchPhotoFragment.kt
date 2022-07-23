package orlov.surf.summer.school.ui.photos

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import orlov.surf.summer.school.R
import orlov.surf.summer.school.databinding.FragmentSearchBinding
import orlov.surf.summer.school.domain.model.Photo


@AndroidEntryPoint
class SearchPhotoFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    val binding
        get() = _binding!!

    private val viewModel: PhotosViewModel by activityViewModels()

    private val adapter = PhotosAdapter(
        ViewHolderType.SMALL,
        object : PhotosAdapter.OnItemClickListener {
            override fun onPhotoClick(photo: Photo) {
                viewModel.setSelectedPhoto(photo)
                findNavController().navigate(R.id.action_searchPhotoFragment_to_photoInfoFragment)
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener { findNavController().navigateUp() }
        binding.rvPhotos.adapter = adapter

        binding.edtSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.searchQuery.postValue(text.toString())
        }
        showKeyBoard()
        setupObservers()
    }

    private fun showKeyBoard() {
        binding.edtSearch.requestFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput( binding.edtSearch, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun setupObservers() {
        viewModel.searchQuery.observe(viewLifecycleOwner) {}

        viewModel.searchedPhotos.observe(viewLifecycleOwner) {
            val text = viewModel.searchQuery.value!!
            if (text.isNotEmpty()) {
                adapter.submitList(it)
            }
            if(text.isEmpty()) {
                setEmptyRequestUi()
            }
            if (it.isEmpty() && text.isNotEmpty()) {
                setEmptyResultUi()
            }
            if (it.isNotEmpty() && text.isNotEmpty()) {
                setSuccessResultUi()
            }
        }
    }

    private fun setEmptyResultUi() {
        binding.rvPhotos.visibility = View.GONE
        binding.groupEmpty.visibility = View.VISIBLE
        binding.groupEnterRequest.visibility = View.GONE
    }

    private fun setSuccessResultUi() {
        binding.rvPhotos.visibility = View.VISIBLE
        binding.groupEmpty.visibility = View.GONE
        binding.groupEnterRequest.visibility = View.GONE
    }

    private fun setEmptyRequestUi() {
        binding.rvPhotos.visibility = View.GONE
        binding.groupEmpty.visibility = View.GONE
        binding.groupEnterRequest.visibility = View.VISIBLE
    }

    private fun showUnlikeDialog(photo: Photo) {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.unlike_alert)
            .setPositiveButton(R.string.alert_yes) { _, _ -> viewModel.likePhoto(photo) }
            .setNegativeButton(R.string.alert_no, null)
            .show()
    }

    override fun onStop() {
        super.onStop()
        viewModel.searchQuery.postValue("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}