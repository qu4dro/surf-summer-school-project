package orlov.surf.summer.school.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import orlov.surf.summer.school.domain.model.User
import orlov.surf.summer.school.domain.usecase.auth.AuthUseCases
import orlov.surf.summer.school.domain.usecase.profile.ProfileUseCases
import orlov.surf.summer.school.utils.LoadState
import orlov.surf.summer.school.utils.Request
import retrofit2.HttpException
import timber.log.Timber
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

    val profileState = MutableLiveData<ProfileUiStates>()

    private val _user = MutableLiveData<User>()
    val user
        get() = _user

    private fun fetchUser() = viewModelScope.launch(Dispatchers.IO) {
        _user.postValue(profileUseCases.fetchUserUseCase.invoke())
    }

    fun logout(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authUseCases.logoutUseCase(token).collect { request ->
                when (request) {
                    is Request.Loading -> {
                        profileState.postValue(ProfileUiStates.LOADING)
                    }
                    is Request.Error -> {
                        profileState.postValue(ProfileUiStates.ERROR)
                    }
                    is Request.Success -> {
                        profileState.postValue(ProfileUiStates.LOGOUT)
                    }
                }
            }
        }
    }
}

enum class ProfileUiStates {
    DEFAULT,
    LOADING,
    ERROR,
    LOGOUT
}