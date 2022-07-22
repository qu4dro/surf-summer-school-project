package orlov.surf.summer.school.domain.repository

import kotlinx.coroutines.flow.Flow
import orlov.surf.summer.school.domain.model.User

interface UserRepository {

    suspend fun clearUserPreferences()

    suspend fun getUserPreferences(): Flow<User>

}