package orlov.surf.summer.school.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import orlov.surf.summer.school.domain.usecase.auth.AuthUseCases
import orlov.surf.summer.school.utils.AuthState
import orlov.surf.summer.school.utils.LoadState
import orlov.surf.summer.school.utils.Request
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authUseCases: AuthUseCases) : ViewModel() {

    private val _loginError = MutableLiveData<LoginError>()
    val loginError
        get() = _loginError
    private val _passwordError = MutableLiveData<PasswordError>()
    val passwordError
        get() = _passwordError

    private var login = ""
    private var password = ""

    val loadState = MutableLiveData<LoadState>()

    private val loginValidator: LoginValidator by lazy {
        LoginValidator(_loginError, _passwordError)
    }

    fun auth() {
        if (loginValidator.isFieldsValid(login, password)) {
            viewModelScope.launch(Dispatchers.IO) {
                authUseCases.loginUseCase("+7$login", password).collect { request ->
                    when (request) {
                        is Request.Loading -> {
                            loadState.postValue(LoadState.LOADING)
                        }
                        is Request.Error -> {
                            loadState.postValue(LoadState.ERROR)
                        }
                        is Request.Success -> {
                            loadState.postValue(LoadState.SUCCESS)
                        }
                    }
                }
            }
        }
    }

    fun setLogin(login: String) {
        this.login = login
    }

    fun setPassword(password: String) {
        this.password = password
    }

}