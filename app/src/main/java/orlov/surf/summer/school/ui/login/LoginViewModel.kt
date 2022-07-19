package orlov.surf.summer.school.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _loginError = MutableLiveData<LoginError>()
    val loginError
        get() = _loginError
    private val _passwordError = MutableLiveData<PasswordError>()
    val passwordError
        get() = _passwordError

    private var login = ""
    private var password = ""


    private val loginValidator: LoginValidator by lazy {
        LoginValidator(_loginError, _passwordError)
    }

    fun auth() {
        if (loginValidator.isFieldsValid(login, password)) {
            viewModelScope.launch(Dispatchers.IO) {
                // логика запроса логина
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