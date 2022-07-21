package orlov.surf.summer.school.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

object RequestUtils {

    fun <T> requestFlow(requestFunc: suspend () -> T): Flow<Request<T>> {
        return flow<Request<T>> {
            emit(Request.Success(requestFunc()))
        }.onStart {
            emit(Request.Loading())
        }.catch { error ->
            Timber.d("Loading error", error)
            emit(Request.Error(error.message ?: ""))
        }
    }

}