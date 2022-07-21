package orlov.surf.summer.school.data.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import orlov.surf.summer.school.data.datastore.UserPreferences
import orlov.surf.summer.school.data.datastore.UserPreferencesSerializer
import orlov.surf.summer.school.data.datastore.mapToPreferences
import orlov.surf.summer.school.data.network.mapper.mapToDomain
import orlov.surf.summer.school.data.network.model.AuthRequest
import orlov.surf.summer.school.data.network.service.AuthService
import orlov.surf.summer.school.domain.model.User
import orlov.surf.summer.school.domain.repository.AuthRepository
import orlov.surf.summer.school.utils.Request
import orlov.surf.summer.school.utils.RequestUtils
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val dataStore: DataStore<UserPreferences>
) : AuthRepository {

    override suspend fun login(login: String, password: String): Flow<Request<User>> {
        return RequestUtils.requestFlow {
            val authResponse = authService.auth(AuthRequest(login, password))
            val user = authResponse.mapToDomain()
            dataStore.updateData { user.mapToPreferences() }
            user
        }
    }

    override suspend fun logout(token: String): Flow<Request<Response<Unit>>> {
        return RequestUtils.requestFlow {
            val logoutResponse = authService.logout("Token $token")
            dataStore.updateData { UserPreferencesSerializer.defaultValue }
            logoutResponse
        }
    }

    override suspend fun checkAuthorization() =
        dataStore.data.first().token != UserPreferencesSerializer.defaultValue.token

}