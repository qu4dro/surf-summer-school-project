package orlov.surf.summer.school.domain.repository

import kotlinx.coroutines.flow.Flow
import orlov.surf.summer.school.domain.model.User
import orlov.surf.summer.school.utils.Request
import retrofit2.Response

interface AuthRepository {

    suspend fun login(login: String, password: String): Flow<Request<User>>

    suspend fun logout(token: String): Flow<Request<Response<Unit>>>

    suspend fun checkAuthorization(): Boolean

}