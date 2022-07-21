package orlov.surf.summer.school.domain.usecase

data class LoginUseCases(
    val isAuthorized: IsUserAuthorizedUseCase,
    val authUser: AuthUserUseCase,
    )