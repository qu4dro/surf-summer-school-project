package orlov.surf.summer.school.data.repository

import kotlinx.coroutines.flow.Flow
import orlov.surf.summer.school.data.network.mapper.mapToDomain
import orlov.surf.summer.school.data.network.model.AuthRequest
import orlov.surf.summer.school.data.network.service.AuthService
import orlov.surf.summer.school.domain.model.User
import orlov.surf.summer.school.domain.repository.AuthRepository
import orlov.surf.summer.school.utils.Request
import orlov.surf.summer.school.utils.RequestUtils
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authService: AuthService) : AuthRepository {

    override suspend fun auth(login: String, password: String): Flow<Request<User>> {
        return RequestUtils.requestFlow {
            val authResponse = authService.auth(AuthRequest(login, password))
            val user = authResponse.mapToDomain()
            //user data save to storage TO DO
            user
        }
    }
}