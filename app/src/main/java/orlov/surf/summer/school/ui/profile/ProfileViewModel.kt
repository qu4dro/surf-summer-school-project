package orlov.surf.summer.school.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import orlov.surf.summer.school.domain.model.User
import orlov.surf.summer.school.domain.usecase.profile.ProfileUseCases
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileUseCases: ProfileUseCases) :
    ViewModel() {

    init {
        fetchUser()
    }

    private val _user = MutableLiveData<User>()
    val user
        get() = _user

    private fun fetchUser() = viewModelScope.launch(Dispatchers.IO) {
        _user.postValue(profileUseCases.fetchUserUseCase.invoke())
    }

}