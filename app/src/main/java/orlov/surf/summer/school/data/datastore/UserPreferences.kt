package orlov.surf.summer.school.data.datastore

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val token: String,
    val userInfo: UserInfoPreferences
)