package orlov.surf.summer.school.domain.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import orlov.surf.summer.school.domain.model.Photo
import orlov.surf.summer.school.utils.Request

interface PhotoRepository {

    suspend fun fetchPhotos(token: String): Flow<Request<List<Photo>>>

    fun fetchPhotosCached(): LiveData<List<Photo>>

}