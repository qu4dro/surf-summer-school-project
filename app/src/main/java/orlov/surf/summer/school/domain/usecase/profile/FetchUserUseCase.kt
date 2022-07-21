package orlov.surf.summer.school.domain.usecase.profile

import orlov.surf.summer.school.data.datastore.mapToDomain
import orlov.surf.summer.school.data.repository.ProfileRepositoryImpl
import orlov.surf.summer.school.data.repository.UserPreferencesRepositoryImpl
import javax.inject.Inject

class FetchUserUseCase @Inject constructor(private val profileRepository: ProfileRepositoryImpl) {
    suspend operator fun invoke() = profileRepository.fetchUser()
}