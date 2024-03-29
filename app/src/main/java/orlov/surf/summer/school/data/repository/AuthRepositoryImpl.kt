package orlov.surf.summer.school.data.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okio.IOException
import orlov.surf.summer.school.data.datastore.UserPreferences
import orlov.surf.summer.school.data.datastore.UserPreferencesSerializer
import orlov.surf.summer.school.data.datastore.mapToPreferences
import orlov.surf.summer.school.data.db.PhotosDao
import orlov.surf.summer.school.data.db.PhotosDatabase
import orlov.surf.summer.school.data.mapper.mapToDomain
import orlov.surf.summer.school.data.network.model.AuthRequest
import orlov.surf.summer.school.data.network.model.LogoutResponse
import orlov.surf.summer.school.data.network.service.AuthService
import orlov.surf.summer.school.domain.model.LogoutInfo
import orlov.surf.summer.school.domain.model.User
import orlov.surf.summer.school.domain.repository.AuthRepository
import orlov.surf.summer.school.utils.Request
import orlov.surf.summer.school.utils.RequestUtils
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val dataStore: DataStore<UserPreferences>,
    private val photosDao: PhotosDao
) : AuthRepository {

    override suspend fun login(login: String, password: String): Flow<Request<User>> {
        return RequestUtils.requestFlow {
            val authResponse = authService.auth(AuthRequest(login, password))
            val user = authResponse.mapToDomain()
            dataStore.updateData { user.mapToPreferences() }
            user
        }
    }

    override suspend fun logout(token: String): Flow<Request<LogoutInfo>> {
        return RequestUtils.requestFlow {
            var formattedResponse: LogoutResponse? = null
            try {
                val rawResponse = authService.logout("Token $token")
                if (rawResponse.code() == 401 || rawResponse.code() == 204) {
                    dataStore.updateData { UserPreferencesSerializer.defaultValue }
                    photosDao.deletePhotos()
                    formattedResponse = LogoutResponse(rawResponse.code().toString(), rawResponse.message())
                }
            } catch (e: IOException) { }
            formattedResponse!!.mapToDomain()
        }
    }

    override suspend fun checkAuthorization() =
        dataStore.data.first().token != UserPreferencesSerializer.defaultValue.token

}
