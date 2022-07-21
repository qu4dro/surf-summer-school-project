package orlov.surf.summer.school.domain.repository

import orlov.surf.summer.school.data.datastore.UserPreferences

interface UserRepository {

    suspend fun clearUserPreferences()

    suspend fun getUserPreferences(): UserPreferences

}