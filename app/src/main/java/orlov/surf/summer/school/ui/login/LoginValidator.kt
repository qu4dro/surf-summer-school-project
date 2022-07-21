package orlov.surf.summer.school.ui.login

import androidx.lifecycle.MutableLiveData

class LoginValidator(
    private val loginError: MutableLiveData<LoginError>,
    private val passwordError: MutableLiveData<PasswordError>
) {

    fun isFieldsValid(login: String, password: String): Boolean {

        val isLoginValid = when {
            login.isEmpty() -> {
                loginError.value = LoginError.EMPTY
                false
            }
            login.length != 10 -> {
                loginError.value = LoginError.NOT_VALID
                false
            }
            else -> {
                loginError.value = LoginError.VALID
                true
            }
        }

        val isPasswordValid = when {
            password.isEmpty() -> {
                passwordError.value = PasswordError.EMPTY
                false
            }
            password.length !in 6..256 -> {
                passwordError.value = PasswordError.NOT_VALID
                false
            }
            else -> {
                passwordError.value = PasswordError.VALID
                true
            }
        }

        return isLoginValid && isPasswordValid

    }

}

enum class LoginError {
    VALID,
    NOT_VALID,
    EMPTY
}

enum class PasswordError {
    VALID,
    NOT_VALID,
    EMPTY
}