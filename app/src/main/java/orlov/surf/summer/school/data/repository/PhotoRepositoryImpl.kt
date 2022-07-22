package orlov.surf.summer.school.data.repository

import kotlinx.coroutines.flow.Flow
import orlov.surf.summer.school.data.network.mapper.mapToDomain
import orlov.surf.summer.school.data.network.service.PhotoService
import orlov.surf.summer.school.domain.model.Photo
import orlov.surf.summer.school.domain.repository.PhotoRepository
import orlov.surf.summer.school.utils.Request
import orlov.surf.summer.school.utils.RequestUtils
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(private val photoService: PhotoService) : PhotoRepository {

    override suspend fun fetchPhotos(token: String): Flow<Request<List<Photo>>> {
        return RequestUtils.requestFlow {
            val response = photoService.fetchPhotos("Token $token")
            val photoList = response.map { it.mapToDomain() }
            photoList
        }
    }

}