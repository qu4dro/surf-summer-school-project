package orlov.surf.summer.school.data.network.service

import orlov.surf.summer.school.data.network.model.AuthRequest
import orlov.surf.summer.school.data.network.model.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface AuthService {

    @POST("auth/login")
    suspend fun auth(@Body authRequest: AuthRequest): AuthResponse

    @POST("auth/logout")
    suspend fun logout(@Header("Authorization") token: String) : Response<Unit>

}

