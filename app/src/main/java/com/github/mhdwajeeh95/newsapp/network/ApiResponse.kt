package com.github.mhdwajeeh95.newsapp.network

import retrofit2.HttpException
import java.io.IOException


/**
 * Common class used to represent Network API Response
 * @param <T> the type of the response object
</T> */
sealed class ApiResponse<out T> {

    companion object {

        /**
         * create an Api response from a [Throwable] object
         * @param error the [Throwable] object
         * @return [ApiResponse]
         */
        fun <T> create(error: Throwable): ApiResponse<T> {

            return when (error) {
                is HttpException -> {
                    var msg = error.message()
                    if (msg.isNullOrEmpty()) {
                        msg = when (error.code()) {
                            400 -> "Bad Request"
                            401 -> "Unauthorized"
                            403 -> "Forbidden"
                            404 -> "Not Found"
                            500 -> "Server Error"
                            else -> "Unknown Error"
                        }
                    }
                    ApiErrorResponse(error.code(), msg)
                }
                is IOException -> NetworkErrorResponse()
                else -> UnknownErrorResponse()
            }
        }

        /**
         * create an Api response from a [T] object
         * @param response the [T] object
         * @return [ApiResponse]
         */
        fun <T> create(response: T): ApiResponse<T> = ApiSuccessResponse(response)

    }
}

/**
 * interface to mark success responses
 */
interface SuccessResponse

/**
 * interface to mark error responses
 */
interface ErrorResponse {
    fun errorMessage(): String
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>(), SuccessResponse

/**
 * class that represents a HTTP success response contains body data of type T
 */
data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>(), SuccessResponse


/**
 * class that represents a HTTP error response contains Http Error Code and Message
 */
data class ApiErrorResponse<T>(val errorCode: Int, private val errorMessage: String) :
    ApiResponse<T>(),
    ErrorResponse {
    override fun errorMessage(): String = errorMessage
}

/**
 * class that represents a Network error response
 */
class NetworkErrorResponse<T>() : ApiResponse<T>(), ErrorResponse {
    override fun errorMessage(): String = "Network Error Occurred"
}

/**
 * class that represents an Unknown Error response
 */
class UnknownErrorResponse<T>() : ApiResponse<T>(), ErrorResponse {
    override fun errorMessage(): String = "Unknown Error Occurred"
}

