package orlov.surf.summer.school.utils

enum class LoadState {
    WAITING,
    LOADING,
    ERROR,
    SUCCESS
}

enum class PhotoLoadState {
    WAITING,
    FIRST_LOADING,
    LOADING,
    ERROR,
    ERROR_OR_EMPTY,
    SUCCESS
}