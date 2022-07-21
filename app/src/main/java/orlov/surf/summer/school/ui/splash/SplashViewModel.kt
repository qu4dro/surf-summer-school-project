package orlov.surf.summer.school.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import orlov.surf.summer.school.data.repository.AuthRepositoryImpl
import orlov.surf.summer.school.utils.AuthState
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val authRepository: AuthRepositoryImpl) :
    ViewModel() {

    val authState = MutableLiveData<AuthState>()

    init {
        checkAuthorization()
    }

    private fun checkAuthorization() {
        viewModelScope.launch(Dispatchers.IO) {
            when (authRepository.checkAuthorization()) {
                true -> authState.postValue(AuthState.AUTHORIZED)
                false -> authState.postValue(AuthState.UNAUTHORIZED)
            }
        }
    }

}