package uz.dsavdo.emakro.network.network

import java.lang.Exception

sealed class Resource<out T : Any> {
    object Loading : Resource<Nothing>()
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
    data class GenericError(val errorResponse: ErrorResponse) : Resource<Nothing>()
}

