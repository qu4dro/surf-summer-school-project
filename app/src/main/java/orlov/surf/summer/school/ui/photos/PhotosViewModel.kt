package orlov.surf.summer.school.ui.photos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import orlov.surf.summer.school.domain.model.Photo
import orlov.surf.summer.school.domain.usecase.photo.PhotoUseCases
import orlov.surf.summer.school.utils.LoadState
import orlov.surf.summer.school.utils.Request
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(private val photoUseCases: PhotoUseCases) : ViewModel() {

    private val _photos = photoUseCases.fetchCachedPhotosUseCase.invoke()
    val photos
        get() = _photos

    private val _savedPhotos = photoUseCases.getSavedPhotosUseCase.invoke()
    val savedPhotos
        get() = _savedPhotos

    private val _selectedPhoto = MutableLiveData<Photo>()
    val selectedPhoto
        get() = _selectedPhoto

    val loadState = MutableLiveData(LoadState.LOADING)

    fun fetchPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            photoUseCases.fetchPhotosUsesCase().collect { request ->
                when (request) {
                    is Request.Loading -> loadState.postValue(LoadState.LOADING)
                    is Request.Error -> loadState.postValue(LoadState.ERROR)
                    is Request.Success -> {
                        loadState.postValue(LoadState.SUCCESS)
                    }
                }
            }
        }
    }

    fun likePhoto(photo: Photo) = viewModelScope.launch(Dispatchers.IO) {
        photoUseCases.likePhotoUseCase.invoke(photo)
    }

    fun setSelectedPhoto(photo: Photo) {
        _selectedPhoto.postValue(photo)
    }

}