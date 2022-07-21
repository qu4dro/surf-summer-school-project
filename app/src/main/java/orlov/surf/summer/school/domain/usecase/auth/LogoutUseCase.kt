package orlov.surf.summer.school.domain.usecase.auth

import orlov.surf.summer.school.data.repository.AuthRepositoryImpl
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val authRepository: AuthRepositoryImpl){

    suspend operator fun invoke(token: String) = authRepository.logout(token)

}