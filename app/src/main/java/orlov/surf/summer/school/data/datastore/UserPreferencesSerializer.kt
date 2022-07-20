package orlov.surf.summer.school.data.datastore

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer : Serializer<UserPreferences> {

    override val defaultValue: UserPreferences
        get() = UserPreferences(false, "-1", UserInfoPreferences("", "", "", "", "", "", "", ""))

    override suspend fun readFrom(input: InputStream): UserPreferences {
        return try {
            Json.decodeFromString(
                deserializer = UserPreferences.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = UserPreferences.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}