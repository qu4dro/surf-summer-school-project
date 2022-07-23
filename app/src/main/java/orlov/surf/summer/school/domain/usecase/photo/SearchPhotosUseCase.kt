package orlov.surf.summer.school.domain.usecase.photo

import orlov.surf.summer.school.data.repository.PhotoRepositoryImpl
import javax.inject.Inject

class SearchPhotosUseCase @Inject constructor(private val photoRepository: PhotoRepositoryImpl) {

    operator fun invoke(searchQuery: String) = photoRepository.searchPhotos(searchQuery)

}