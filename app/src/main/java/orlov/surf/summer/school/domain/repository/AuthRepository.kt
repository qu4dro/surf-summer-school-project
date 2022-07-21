package orlov.surf.summer.school.domain.repository

import kotlinx.coroutines.flow.Flow
import orlov.surf.summer.school.domain.model.User
import orlov.surf.summer.school.utils.Request

interface AuthRepository {

    suspend fun auth(login: String, password: String): Flow<Request<User>>

}