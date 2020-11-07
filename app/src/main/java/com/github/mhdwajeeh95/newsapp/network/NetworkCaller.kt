package com.github.mhdwajeeh95.newsapp.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * static utility class contains safe api request methods
 */
object NetworkCaller {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResponse<T> {
        return try {
            val response: T = withContext(Dispatchers.IO) {
                apiCall.invoke()
            }
            ApiResponse.create(response);
        } catch (throwable: Throwable) {
            ApiResponse.create(throwable)
        }
    }
}