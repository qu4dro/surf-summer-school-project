package orlov.surf.summer.school.domain.usecase

import orlov.surf.summer.school.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(login: String, password: String) =
        authRepository.auth(login, password)
}