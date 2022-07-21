package orlov.surf.summer.school.domain.repository

import orlov.surf.summer.school.data.datastore.UserPreferences

interface UserPreferencesRepository {

    suspend fun updateUserPreferences(userPreferences: UserPreferences)

    suspend fun getUserPreferences(): UserPreferences

}