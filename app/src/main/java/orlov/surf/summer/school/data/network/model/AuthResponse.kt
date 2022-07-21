package orlov.surf.summer.school.data.network.model

import com.squareup.moshi.Json

data class AuthResponse(
    @Json(name = "token")
    val token: String,
    @Json(name = "user_info")
    val userInfo: UserInfoObj
)

