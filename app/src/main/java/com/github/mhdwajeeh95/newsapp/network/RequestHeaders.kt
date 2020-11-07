package com.github.mhdwajeeh95.newsapp.network

import javax.inject.Inject
import javax.inject.Singleton

/**
 * encapsulates a Map that contains the headers that should be sent within the HTTP request
 */
@Singleton
class RequestHeaders @Inject constructor() {

    val headers: HashMap<String, String> = hashMapOf(
        "x-api-key" to NetworkConst.API_KEY
    )

}
