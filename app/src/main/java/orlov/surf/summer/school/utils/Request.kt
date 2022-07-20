package orlov.surf.summer.school.utils

sealed class Request<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : Request<T>(data)
    class Error<T>(error: Throwable, data: T? = null) : Request<T>(data, error)
    class Loading<T> : Request<T>()
}