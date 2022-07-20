package orlov.surf.summer.school.data.datastore

import androidx.datastore.core.Serializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
data class UserPreferences(
    val isAuthorized: Boolean,
    val token: String,
    val userInfo: UserInfoPreferences
)