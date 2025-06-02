package com.hugo.utilities


//sealed class Resource<T>(val data: T? = null, val message: String? = null) {
//    class Success<T>(data: T) : Resource<T>(data)
//    class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)
//    //    class Loading<T>(data: T? = null) : Resource<T>(data)
//    class Loading<T>(val isFetchingFromNetwork: Boolean = false, data: T? = null) : Resource<T>(data)
//// This can be used to indicate if the loading is from network or cache
//}



sealed class Resource<D, out E : AppError>(
    val data: D? = null,
    val error: E? = null
) {
    class Success<D>(data: D) : Resource<D, AppError>(data = data)
    class Error<D, out E : AppError>(error: E, data: D? = null) : Resource<D, E>(data = data, error = error)
    class Loading<D, out E : AppError>(
        val isFetchingFromNetwork: Boolean = false,
        data: D? = null
    ) : Resource<D, E>(data = data, error = null)
}

