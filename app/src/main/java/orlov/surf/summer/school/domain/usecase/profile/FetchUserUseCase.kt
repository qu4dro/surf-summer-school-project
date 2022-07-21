package orlov.surf.summer.school.domain.usecase.profile

import orlov.surf.summer.school.data.repository.ProfileRepositoryImpl
import javax.inject.Inject

class FetchUserUseCase @Inject constructor(private val profileRepository: ProfileRepositoryImpl) {

    suspend operator fun invoke() = profileRepository.fetchUser()

}