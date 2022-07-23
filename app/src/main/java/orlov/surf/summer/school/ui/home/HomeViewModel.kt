package orlov.surf.summer.school.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import orlov.surf.summer.school.domain.model.Photo
import orlov.surf.summer.school.domain.usecase.photo.PhotoUseCases
import orlov.surf.summer.school.utils.LoadState
import orlov.surf.summer.school.utils.PhotoLoadState
import orlov.surf.summer.school.utils.Request
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val photoUseCases: PhotoUseCases) : ViewModel() {

    private val _photos = photoUseCases.fetchCachedPhotosUseCase.invoke()
    val photos
        get() = _photos

    val loadState = MutableLiveData(LoadState.LOADING)
    var isFirstLoading = true

    fun fetchPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            photoUseCases.fetchPhotosUsesCase().collect { request ->
                when (request) {
                    is Request.Loading -> loadState.postValue(LoadState.LOADING)
                    is Request.Error -> loadState.postValue(LoadState.ERROR)
                    is Request.Success -> { loadState.postValue(LoadState.SUCCESS) }
                    }
                }
            }
        }
    }