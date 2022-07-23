package orlov.surf.summer.school.domain.model

data class Photo(
    val id: String,
    val title: String,
    val content: String,
    val photoUrl: String,
    val publicationDate: Long,
    var isLiked: Boolean = false
)