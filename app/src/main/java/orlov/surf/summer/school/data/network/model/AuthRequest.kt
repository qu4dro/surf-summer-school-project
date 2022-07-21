package orlov.surf.summer.school.data.network.model

import com.squareup.moshi.Json

data class AuthRequest(
    @Json(name = "phone")
    val login: String,
    @Json(name = "password")
    val password: String
)
