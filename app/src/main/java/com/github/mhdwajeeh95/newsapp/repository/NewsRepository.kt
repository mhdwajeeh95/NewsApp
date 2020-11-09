package com.github.mhdwajeeh95.newsapp.repository

import com.github.mhdwajeeh95.newsapp.db.daos.ArticleDao
import com.github.mhdwajeeh95.newsapp.models.Article
import com.github.mhdwajeeh95.newsapp.network.ApiResponse
import com.github.mhdwajeeh95.newsapp.network.NetworkCaller
import com.github.mhdwajeeh95.newsapp.network.models.ArticlesResponseBody
import com.github.mhdwajeeh95.newsapp.network.services.ApiService
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val articleDao: ArticleDao,
    private val apiService: ApiService
) {

    suspend fun searchNews(query: String, page: Int = 1): ApiResponse<ArticlesResponseBody> {

        return NetworkCaller.safeApiCall {
            apiService.everything(
                queryString = query,
                page = page
            )
        };
    }

    suspend fun getTopNews(
        country: String = "tr",
        page: Int = 1
    ): ApiResponse<ArticlesResponseBody> {

        return NetworkCaller.safeApiCall {
            apiService.topHeadlines(
                country = country,
                page = page
            )
        };
    }

    suspend fun getFavoriteArticles() = articleDao.getFavoriteArticles()

    suspend fun deleteFavoriteArticle(article: Article) = articleDao.deleteArticle(article)


}