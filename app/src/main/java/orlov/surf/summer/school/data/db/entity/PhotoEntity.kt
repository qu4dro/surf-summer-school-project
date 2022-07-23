package orlov.surf.summer.school.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val content: String,
    val photoUrl: String,
    val publicationDate: Long,
    val isLiked: Boolean = false
)

