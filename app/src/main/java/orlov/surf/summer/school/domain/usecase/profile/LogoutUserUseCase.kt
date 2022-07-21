package orlov.surf.summer.school.domain.usecase.profile

import orlov.surf.summer.school.data.repository.ProfileRepositoryImpl
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(private val profileRepository: ProfileRepositoryImpl){

    suspend operator fun invoke(token: String) = profileRepository.logout(token)

}