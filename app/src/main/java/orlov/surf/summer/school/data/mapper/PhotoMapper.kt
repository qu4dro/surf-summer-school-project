package orlov.surf.summer.school.data.mapper

import orlov.surf.summer.school.data.db.entity.PhotoEntity
import orlov.surf.summer.school.data.network.model.PhotoObj
import orlov.surf.summer.school.domain.model.Photo

fun PhotoObj.mapToDomain(): Photo {
    return Photo(
        this.id,
        this.title,
        this.content,
        this.photoUrl,
        this.publicationDate,
        false
    )
}

fun PhotoObj.mapToEntity(): PhotoEntity {
    return PhotoEntity(
        this.id,
        this.title,
        this.content,
        this.photoUrl,
        this.publicationDate,
        false
    )
}

fun Photo.mapToEntity(): PhotoEntity {
    return PhotoEntity(
        this.id,
        this.title,
        this.content,
        this.photoUrl,
        this.publicationDate,
        this.isLiked,
    )
}

fun PhotoEntity.mapToDomain(): Photo {
    return Photo(
        this.id,
        this.title,
        this.content,
        this.photoUrl,
        this.publicationDate,
        this.isLiked
    )
}