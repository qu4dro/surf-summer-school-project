package orlov.surf.summer.school.data.repository


import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.first
import orlov.surf.summer.school.data.datastore.UserPreferences
import orlov.surf.summer.school.domain.repository.UserPreferencesRepository

class UserPreferencesRepositoryImpl(
    private val userPreferencesStore: DataStore<UserPreferences>
) : UserPreferencesRepository {

    override suspend fun updateUserPreferences(userPreferences: UserPreferences) {
        userPreferencesStore.updateData { userPreferences }
    }

    override suspend fun getUserPreferences() = userPreferencesStore.data.first()

}