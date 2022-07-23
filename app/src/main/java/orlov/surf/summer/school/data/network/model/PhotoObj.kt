package orlov.surf.summer.school.data.network.model

import com.squareup.moshi.Json

data class PhotoObj(
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "content")
    val content: String,
    @Json(name = "photoUrl")
    val photoUrl: String,
    @Json(name = "publicationDate")
    val publicationDate: Long
)