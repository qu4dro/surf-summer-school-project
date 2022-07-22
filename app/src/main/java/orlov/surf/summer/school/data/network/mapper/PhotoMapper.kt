package orlov.surf.summer.school.data.network.mapper

import orlov.surf.summer.school.data.network.model.PhotoObj
import orlov.surf.summer.school.domain.model.Photo

fun PhotoObj.mapToDomain(): Photo {
    return Photo(
        this.id,
        this.title,
        this.content,
        this.photoUrl,
        this.publicationDate
    )
}