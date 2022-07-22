package orlov.surf.summer.school.domain.repository

import kotlinx.coroutines.flow.Flow
import orlov.surf.summer.school.domain.model.Photo
import orlov.surf.summer.school.utils.Request

interface PhotoRepository {

    suspend fun fetchPhotos(token: String): Flow<Request<List<Photo>>>

}