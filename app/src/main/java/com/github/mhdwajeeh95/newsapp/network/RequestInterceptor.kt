package com.github.mhdwajeeh95.newsapp.network

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.lang.StringBuilder
import javax.inject.Inject
import javax.inject.Singleton


/**
 * An implementation of [okhttp3.Interceptor] interface
 */

@Singleton
class RequestInterceptor @Inject constructor(
    private val requestHeaders: RequestHeaders
) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // get the original request
        val originalRequest = chain.request()

        // create new request builder from the original request
        val builder = originalRequest.newBuilder()
            .method(originalRequest.method, originalRequest.body)

        // set headers to the new request from requestHeaders object
        for ((headerKey, headerValue) in requestHeaders.headers) {
            builder.header(headerKey, headerValue)
        }

        // build and proceed with new request to get response
        val originalResponse = chain.proceed(builder.build())

        // logging the response error if not successful
        this.log(originalResponse)

        return originalResponse
    }

    private fun log(response: Response) {
        if (!response.isSuccessful) {
            try {
                Timber.tag(NetworkConst.HTTP_ERROR_TAG).e(
                    StringBuilder().apply {
                        append(response.code)
                        append(" | ")
                        append(response.request.method)
                        append(" | ")
                        append(response.request.url)
                        append("\n")
                        append("Headers: ")
                        append(response.request.headers)
                        append("\n")
                        append("Body: ")
                        append(response.request.body)
                    }.toString()
                )
            } finally {

            }

        }
    }
}