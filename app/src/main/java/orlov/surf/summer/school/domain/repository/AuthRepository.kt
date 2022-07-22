package orlov.surf.summer.school.domain.repository

import kotlinx.coroutines.flow.Flow
import orlov.surf.summer.school.data.network.model.LogoutResponse
import orlov.surf.summer.school.domain.model.LogoutInfo
import orlov.surf.summer.school.domain.model.User
import orlov.surf.summer.school.utils.Request

interface AuthRepository {

    suspend fun login(login: String, password: String): Flow<Request<User>>

    suspend fun logout(token: String): Flow<Request<LogoutInfo>>

    suspend fun checkAuthorization(): Boolean

}