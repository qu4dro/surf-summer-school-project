package orlov.surf.summer.school.domain.usecase.profile

import orlov.surf.summer.school.data.datastore.mapToDomain
import orlov.surf.summer.school.data.repository.UserRepositoryImpl
import javax.inject.Inject

class FetchUserUseCase @Inject constructor(private val userRepository: UserRepositoryImpl) {

    suspend operator fun invoke() = userRepository.getUserPreferences().mapToDomain()

}