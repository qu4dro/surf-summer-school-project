package orlov.surf.summer.school.data.datastore

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoPreferences(
    val id: String,
    val phone: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String,
    val city: String,
    val about: String
)