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
import orlov.surf.summer.school.utils.Resource
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

    val loadState = MutableLiveData<LoadState>()

    private val _user = MutableLiveData<User>()
    val user
        get() = _user

    private fun fetchUser() = viewModelScope.launch(Dispatchers.IO) {
        _user.postValue(profileUseCases.fetchUserUseCase.invoke())
    }

    fun logout(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = authUseCases.logoutUseCase(token)
            when (response) {
                is Resource.Loading -> {
                    loadState.postValue(LoadState.LOADING)
                }
                is Resource.Error -> {
                    loadState.postValue(LoadState.ERROR)
                }
                is Resource.Success -> {
                    loadState.postValue(LoadState.SUCCESS)
                }

                else -> {}
            }
        }
    }


}