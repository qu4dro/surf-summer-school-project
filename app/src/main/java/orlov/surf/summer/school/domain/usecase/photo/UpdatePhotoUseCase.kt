package orlov.surf.summer.school.domain.usecase.photo

import orlov.surf.summer.school.data.repository.PhotoRepositoryImpl
import orlov.surf.summer.school.domain.model.Photo
import javax.inject.Inject

class UpdatePhotoUseCase @Inject constructor(private val photoRepository: PhotoRepositoryImpl) {

    suspend operator fun invoke(photo: Photo) = photoRepository.updatePhoto(photo)

}