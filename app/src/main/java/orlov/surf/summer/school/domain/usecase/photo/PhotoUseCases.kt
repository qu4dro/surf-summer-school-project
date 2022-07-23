package orlov.surf.summer.school.domain.usecase.photo

data class PhotoUseCases(
    val fetchPhotosUsesCase: FetchPhotosUsesCase,
    val fetchCachedPhotosUseCase: FetchCachedPhotosUseCase,
    val likePhotoUseCase: LikePhotoUseCase,
    val getSavedPhotosUseCase: GetSavedPhotosUseCase,
    val searchPhotosUseCase: SearchPhotosUseCase
)