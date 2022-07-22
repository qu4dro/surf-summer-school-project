package orlov.surf.summer.school.domain.usecase.photo

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.first
import orlov.surf.summer.school.data.datastore.UserPreferences
import orlov.surf.summer.school.data.repository.PhotoRepositoryImpl
import javax.inject.Inject

class FetchPhotosUsesCase @Inject constructor(private val photoRepository: PhotoRepositoryImpl, private val dataStore: DataStore<UserPreferences>) {

    suspend operator fun invoke()  = photoRepository.fetchPhotos(dataStore.data.first().token)

}