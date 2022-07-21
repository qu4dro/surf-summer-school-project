package orlov.surf.summer.school.domain.usecase

import orlov.surf.summer.school.data.repository.UserPreferencesRepositoryImpl

import javax.inject.Inject

class IsUserAuthorizedUseCase @Inject constructor(private val userPreferencesRepository: UserPreferencesRepositoryImpl)  {
    suspend operator fun invoke() = userPreferencesRepository.getUserPreferences().isAuthorized
}