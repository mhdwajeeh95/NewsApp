package com.github.mhdwajeeh95.newsapp.network.services

import com.github.mhdwajeeh95.newsapp.network.models.ArticlesResponseBody
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("everything")
    suspend fun everything(
        @Query("q") queryString: String,
        @Query("qInTitle") queryInTitle: String = "",
        @Query("sources") sources: String = "",
        @Query("domains") domains: String = "",
        @Query("excludeDomains") excludeDomains: String = "",
        @Query("from") from: String = "",
        @Query("to") to: String = "",
        @Query("language") language: String = "",
        @Query("sortBy") sortBy: String = "",
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 1
    ): ArticlesResponseBody


    @GET("top-headlines")
    suspend fun topHeadlines(
        @Query("q") queryString: String = "",
        @Query("sources") sources: String = "",
        @Query("category") category: String = "",
        @Query("country") country: String = "",
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 1
    ): ArticlesResponseBody

}