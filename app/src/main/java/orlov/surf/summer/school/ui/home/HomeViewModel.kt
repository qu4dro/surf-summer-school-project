package orlov.surf.summer.school.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import orlov.surf.summer.school.domain.model.Photo
import orlov.surf.summer.school.domain.usecase.photo.PhotoUseCases
import orlov.surf.summer.school.utils.Request
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val photoUseCases: PhotoUseCases) : ViewModel() {

    private val _photos = MutableLiveData<List<Photo>>()
    val photos
        get() = _photos

    init {
        fetchPhotos()
    }

    fun fetchPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            photoUseCases.fetchPhotosUsesCase().collect { request ->
                when (request) {
                    is Request.Loading -> {
                        Timber.d("Loading")
                    }
                    is Request.Error -> {
                        Timber.d("Error")
                    }
                    is Request.Success -> {
                        Timber.d("Success")
                        _photos.postValue(request.data)
                    }
                }
            }
        }
    }

}