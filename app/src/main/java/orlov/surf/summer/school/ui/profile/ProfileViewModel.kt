package orlov.surf.summer.school.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import orlov.surf.summer.school.domain.model.User
import orlov.surf.summer.school.domain.usecase.auth.AuthUseCases
import orlov.surf.summer.school.domain.usecase.profile.ProfileUseCases
import orlov.surf.summer.school.utils.LoadState
import orlov.surf.summer.school.utils.Request
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val authUseCases: AuthUseCases
) :
    ViewModel() {

    init {
        fetchUser()
    }

    private val _profileState = MutableLiveData(LoadState.WAITING)
    val profileState
        get() = _profileState

    private var _user = MutableLiveData<User>()
    val user
        get() = _user

    private fun fetchUser() = viewModelScope.launch(Dispatchers.IO) {
        profileUseCases.fetchUserUseCase.invoke().collect {
            _user.postValue(it)
        }
    }

    fun logout(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authUseCases.logoutUseCase(token).collect { request ->
                when (request) {
                    is Request.Loading -> {
                        _profileState.postValue(LoadState.LOADING)
                    }
                    is Request.Error -> {
                        _profileState.postValue(LoadState.ERROR)
                    }
                    is Request.Success -> {
                        _profileState.postValue(LoadState.SUCCESS)
                    }
                }
            }
        }
    }

    fun navigationComplete() {
        _profileState.postValue(LoadState.WAITING)
    }
}