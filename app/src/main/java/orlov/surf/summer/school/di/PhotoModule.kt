package orlov.surf.summer.school.di

import androidx.datastore.core.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import orlov.surf.summer.school.data.datastore.UserPreferences
import orlov.surf.summer.school.data.db.PhotosDao
import orlov.surf.summer.school.data.network.service.PhotoService
import orlov.surf.summer.school.data.repository.PhotoRepositoryImpl
import orlov.surf.summer.school.domain.usecase.photo.FetchCachedPhotosUseCase
import orlov.surf.summer.school.domain.usecase.photo.FetchPhotosUsesCase
import orlov.surf.summer.school.domain.usecase.photo.PhotoUseCases
import orlov.surf.summer.school.domain.usecase.photo.UpdatePhotoUseCase
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoModule {

    @Provides
    @Singleton
    fun providePhotoService(retrofit: Retrofit): PhotoService =
        retrofit.create(PhotoService::class.java)

    @Provides
    @Singleton
    fun providePhotoRepository(photoService: PhotoService, photosDao: PhotosDao) =
        PhotoRepositoryImpl(photoService, photosDao)

    @Provides
    @Singleton
    fun provideFetchPhotosUseCase(
        photoRepository: PhotoRepositoryImpl,
        dataStore: DataStore<UserPreferences>
    ) = FetchPhotosUsesCase(photoRepository, dataStore)

    @Provides
    @Singleton
    fun provideFetchCachedPhotosUseCase(photoRepository: PhotoRepositoryImpl) =
        FetchCachedPhotosUseCase(photoRepository)

    @Provides
    @Singleton
    fun provideUpdatePhotoUseCase(photoRepository: PhotoRepositoryImpl) =
        UpdatePhotoUseCase(photoRepository)

    @Provides
    @Singleton
    fun providePhotoUseCases(
        fetchPhotosUseCase: FetchPhotosUsesCase,
        fetchCachedPhotosUseCase: FetchCachedPhotosUseCase,
        updatePhotoUseCase: UpdatePhotoUseCase
    ) = PhotoUseCases(fetchPhotosUseCase, fetchCachedPhotosUseCase, updatePhotoUseCase)

}