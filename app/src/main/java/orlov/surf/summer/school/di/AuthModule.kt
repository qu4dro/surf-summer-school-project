package orlov.surf.summer.school.di

import androidx.datastore.core.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import orlov.surf.summer.school.data.datastore.UserPreferences
import orlov.surf.summer.school.data.network.service.AuthService
import orlov.surf.summer.school.data.repository.AuthRepositoryImpl
import orlov.surf.summer.school.data.repository.UserPreferencesRepositoryImpl
import orlov.surf.summer.school.domain.usecase.AuthUserUseCase
import orlov.surf.summer.school.domain.usecase.IsUserAuthorizedUseCase
import orlov.surf.summer.school.domain.usecase.LoginUseCases
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService, dataStore: DataStore<UserPreferences>) = AuthRepositoryImpl(authService, dataStore)

    @Provides
    @Singleton
    fun provideAuthUserUseCase(authRepository: AuthRepositoryImpl) = AuthUserUseCase(authRepository)

    @Provides
    @Singleton
    fun provideCheckUserAuthorizedUseCase(userPreferencesRepository: UserPreferencesRepositoryImpl) = IsUserAuthorizedUseCase(userPreferencesRepository)

    @Provides
    @Singleton
    fun provideAuthUseCases(isUserAuthorized: IsUserAuthorizedUseCase, authUserUseCase: AuthUserUseCase) = LoginUseCases(isUserAuthorized, authUserUseCase)

}