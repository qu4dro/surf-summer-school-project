package orlov.surf.summer.school.utils

sealed class Request<T> {
    class Loading<T> : Request<T>()
    data class Success<T>(internal val data: T?) : Request<T>()
    data class Error<T>(internal val message: String?) : Request<T>()
}