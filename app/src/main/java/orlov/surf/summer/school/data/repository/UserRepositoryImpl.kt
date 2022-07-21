package orlov.surf.summer.school.data.repository


import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.first
import orlov.surf.summer.school.data.datastore.UserPreferences
import orlov.surf.summer.school.data.datastore.UserPreferencesSerializer
import orlov.surf.summer.school.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userPreferencesStore: DataStore<UserPreferences>
) : UserRepository {

    override suspend fun clearUserPreferences() {
        userPreferencesStore.updateData { UserPreferencesSerializer.defaultValue }
    }

    override suspend fun getUserPreferences() = userPreferencesStore.data.first()

}