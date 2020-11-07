package com.github.mhdwajeeh95.newsapp.network.services

import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("everything")
    suspend fun everything(@Query("q") queryString:String)

}