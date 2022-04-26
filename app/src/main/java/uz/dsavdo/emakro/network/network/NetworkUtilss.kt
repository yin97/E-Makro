package uz.dsavdo.emakro.network.network

import android.util.Log
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException

suspend fun <T : Any> safeApiCall(
    apiCall: suspend () -> retrofit2.Response<T>,
): Resource<T> {
    try {
        val response = apiCall()
        return if (response.isSuccessful && response.body() != null) {
            Resource.Success<T>(response.body() as T)
        } else {
            if (response.errorBody() != null) {
                val errorResponse =
                        ErrorResponse(jsonResponse = JSONObject(response.errorBody()!!.string()))
                Resource.GenericError(errorResponse)
            } else {
                val errorResponse =
                        ErrorResponse(jsonResponse = JSONObject(response.errorBody()!!.string()))
                Resource.GenericError(errorResponse)
            }
        }
    } catch (throwable: Throwable) {
        Log.d("ErrorTag", throwable.message.toString())
        when (throwable) {
            is ConnectException,
            is NoConnectivityException,
            -> {
                return Resource.Error(NoConnectivityException())
            }
            is HttpException -> {
                val errorResponse: ErrorResponse = throwable.response()?.body() as ErrorResponse
                return Resource.GenericError(errorResponse)
            }
            is IOException -> {
                return Resource.Error(Exception("IOException: " + throwable.message))
            }
            else -> {
                return Resource.Error(Exception(throwable.message))
            }
        }
    }
}
