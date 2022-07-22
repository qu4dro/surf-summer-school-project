package orlov.surf.summer.school.data.repository


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import okio.IOException
import orlov.surf.summer.school.data.datastore.UserPreferences
import orlov.surf.summer.school.data.datastore.UserPreferencesSerializer
import orlov.surf.summer.school.data.datastore.mapToDomain
import orlov.surf.summer.school.domain.model.User
import orlov.surf.summer.school.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userPreferencesStore: DataStore<UserPreferences>
) : UserRepository {

    override suspend fun clearUserPreferences() {
        userPreferencesStore.updateData { UserPreferencesSerializer.defaultValue }
    }

    override suspend fun getUserPreferences(): Flow<User> =
        userPreferencesStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(UserPreferencesSerializer.defaultValue)
                }
            }.map { preferences ->
                preferences.mapToDomain()
            }

}