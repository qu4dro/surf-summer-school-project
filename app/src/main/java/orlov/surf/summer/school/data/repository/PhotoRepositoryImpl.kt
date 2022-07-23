package orlov.surf.summer.school.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import kotlinx.coroutines.flow.Flow
import orlov.surf.summer.school.data.db.PhotosDao
import orlov.surf.summer.school.data.mapper.mapToDomain
import orlov.surf.summer.school.data.mapper.mapToEntity
import orlov.surf.summer.school.data.network.service.PhotoService
import orlov.surf.summer.school.domain.model.Photo
import orlov.surf.summer.school.domain.repository.PhotoRepository
import orlov.surf.summer.school.utils.Request
import orlov.surf.summer.school.utils.RequestUtils
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(private val photoService: PhotoService, private val photosDao: PhotosDao) : PhotoRepository {

    override suspend fun fetchPhotos(token: String): Flow<Request<List<Photo>>> {
        return RequestUtils.requestFlow {
            val response = photoService.fetchPhotos("Token $token")
            photosDao.updatePhotos(response.map { it.mapToEntity() })
            val photoList = response.map { it.mapToDomain() }
            photoList
        }
    }

    override fun fetchPhotosCached(): LiveData<List<Photo>> {
        return Transformations.map(photosDao.getAllPhotos()) { it -> it.map { it.mapToDomain() } }}

}
