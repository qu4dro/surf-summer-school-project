package orlov.surf.summer.school.di

import androidx.datastore.core.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import orlov.surf.summer.school.data.datastore.UserPreferences
import orlov.surf.summer.school.data.network.service.AuthService
import orlov.surf.summer.school.data.repository.ProfileRepositoryImpl
import orlov.surf.summer.school.domain.usecase.profile.FetchUserUseCase
import orlov.surf.summer.school.domain.usecase.profile.LogoutUserUseCase
import orlov.surf.summer.school.domain.usecase.profile.ProfileUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileRepository(dataStore: DataStore<UserPreferences>, authService: AuthService) = ProfileRepositoryImpl(dataStore, authService)

    @Provides
    @Singleton
    fun provideFetchUserUseCase(profileRepository: ProfileRepositoryImpl) = FetchUserUseCase(profileRepository)

    @Provides
    @Singleton
    fun provideLogoutUserUseCase(profileRepository: ProfileRepositoryImpl) = LogoutUserUseCase(profileRepository)

    @Provides
    @Singleton
    fun provideProfileUseCases(fetchUserUseCase: FetchUserUseCase, logoutUserUseCase: LogoutUserUseCase) = ProfileUseCases(logoutUserUseCase, fetchUserUseCase)

}