package orlov.surf.summer.school.domain.usecase.photo

data class PhotoUseCases(
    val fetchPhotosUsesCase: FetchPhotosUsesCase,
    val fetchCachedPhotosUseCase: FetchCachedPhotosUseCase,
    val updatePhotoUseCase: UpdatePhotoUseCase
)