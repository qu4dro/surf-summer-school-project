package orlov.surf.summer.school.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import orlov.surf.summer.school.data.datastore.UserPreferences
import orlov.surf.summer.school.data.datastore.UserPreferencesSerializer
import orlov.surf.summer.school.data.repository.UserPreferencesRepositoryImpl
import javax.inject.Singleton


const val DATA_STORE_FILE_NAME = "user_prefs.pb"

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Singleton
    @Provides
    fun provideProtoDataStore(@ApplicationContext appContext: Context): DataStore<UserPreferences> {
        return DataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            produceFile = { appContext.dataStoreFile(DATA_STORE_FILE_NAME) },
            corruptionHandler = null
        )
    }

    @Singleton
    @Provides
    fun provideUserPreferencesRepository(dataStore: DataStore<UserPreferences>) = UserPreferencesRepositoryImpl(dataStore)

}