package orlov.surf.summer.school.data.network.model

import com.squareup.moshi.Json

data class UserInfoObj(
    @Json(name = "id")
    val id: String,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "lastName")
    val lastName: String,
    @Json(name = "avatar")
    val avatar: String,
    @Json(name = "city")
    val city: String,
    @Json(name = "about")
    val about: String
)
