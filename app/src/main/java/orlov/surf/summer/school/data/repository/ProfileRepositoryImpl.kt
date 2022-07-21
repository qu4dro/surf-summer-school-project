package orlov.surf.summer.school.data.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.first
import orlov.surf.summer.school.data.datastore.UserPreferences
import orlov.surf.summer.school.data.datastore.mapToDomain
import orlov.surf.summer.school.domain.model.User
import orlov.surf.summer.school.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val dataStore: DataStore<UserPreferences>): ProfileRepository {

    override suspend fun logout() {
    }

    override suspend fun fetchUser(): User {
        return dataStore.data.first().mapToDomain()
    }
}