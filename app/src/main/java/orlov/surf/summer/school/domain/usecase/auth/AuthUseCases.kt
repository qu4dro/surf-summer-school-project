package orlov.surf.summer.school.domain.usecase.auth

data class AuthUseCases(
    val loginUseCase: LoginUseCase,
    val logoutUseCase: LogoutUseCase
)
