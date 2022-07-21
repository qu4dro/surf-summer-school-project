package orlov.surf.summer.school.domain.repository

import kotlinx.coroutines.flow.Flow
import orlov.surf.summer.school.domain.model.User
import orlov.surf.summer.school.utils.Request
import retrofit2.Response

interface ProfileRepository {

    suspend fun logout(token: String): Flow<Request<Response<Unit>>>

    suspend fun fetchUser(): User

}