package orlov.surf.summer.school.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import orlov.surf.summer.school.data.db.PhotosDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePhotosDatabase(@ApplicationContext context: Context): PhotosDatabase {
        return PhotosDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun providePhotosDao(db: PhotosDatabase) = db.getPhotosDao()

}