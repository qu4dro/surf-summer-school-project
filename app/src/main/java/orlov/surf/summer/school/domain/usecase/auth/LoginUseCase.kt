package orlov.surf.summer.school.domain.usecase.auth

import orlov.surf.summer.school.data.repository.AuthRepositoryImpl
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepositoryImpl) {
    suspend operator fun invoke(login: String, password: String) =
        authRepository.login(login, password)
}