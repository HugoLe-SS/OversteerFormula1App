package com.hugo.utilities


data class RemoteErrorWithCode(
    val error: AppError.RemoteError,
    val code: Int = 0,
    val specificMessage: String? = null
): AppError

sealed interface AppError {
    enum class RemoteError :AppError {
        REQUEST_TIMEOUT_ERROR,
        TOO_MANY_REQUESTS_ERROR,
        CLIENT_ERROR,
        REDIRECTION_ERROR,
        SERVER_ERROR,
        NO_INTERNET_ERROR,
        SERIALIZATION_ERROR,
        UNKNOWN_ERROR
    }

    enum class LocalError : AppError {
        DATABASE_ERROR,
        CACHE_ERROR,
        DISK_FULL_ERROR,
        UNKNOWN_ERROR
    }

}