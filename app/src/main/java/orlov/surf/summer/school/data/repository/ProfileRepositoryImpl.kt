package orlov.surf.summer.school.data.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import orlov.surf.summer.school.data.datastore.UserPreferences
import orlov.surf.summer.school.data.datastore.mapToDomain
import orlov.surf.summer.school.data.network.service.AuthService
import orlov.surf.summer.school.domain.model.User
import orlov.surf.summer.school.domain.repository.ProfileRepository
import orlov.surf.summer.school.utils.Request
import orlov.surf.summer.school.utils.RequestUtils
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val dataStore: DataStore<UserPreferences>, private val authService: AuthService): ProfileRepository {

    override suspend fun logout(token: String): Flow<Request<Response<Unit>>> {
        return RequestUtils.requestFlow {
            Timber.d("BEFORE")
            val logoutResponse = authService.logout("Token $token")
            Timber.d("AFTER")
            //dataStore.updateData { UserPreferencesSerializer.defaultValue }
            logoutResponse
        }
    }

    override suspend fun fetchUser(): User {
        return dataStore.data.first().mapToDomain()
    }
}