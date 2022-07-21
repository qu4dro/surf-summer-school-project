package orlov.surf.summer.school.utils

sealed class Resource<T : Any> {
    class Success<T: Any>(val data: T?) : Resource<T>()
    class Error<T: Any>(val code: Int, val message: String?) : Resource<T>()
    class Exception<T: Any>(val e: Throwable) : Resource<T>()
    class Loading<T: Any>(): Resource<T>()
}