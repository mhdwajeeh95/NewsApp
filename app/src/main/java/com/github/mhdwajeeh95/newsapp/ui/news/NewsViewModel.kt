package com.github.mhdwajeeh95.newsapp.ui.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mhdwajeeh95.newsapp.models.Article
import com.github.mhdwajeeh95.newsapp.network.ApiEmptyResponse
import com.github.mhdwajeeh95.newsapp.network.ApiSuccessResponse
import com.github.mhdwajeeh95.newsapp.network.SuccessResponse
import com.github.mhdwajeeh95.newsapp.network.models.ArticlesResponseBody
import com.github.mhdwajeeh95.newsapp.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    app: Application,
    private val newsRepository: NewsRepository
) : AndroidViewModel(app) {


    private val _recyclerLoadingStatus =
        MutableLiveData(RecyclerLoadingStatus.PARTIALLY_LOADED)
    val recyclerLoadingStatus: LiveData<RecyclerLoadingStatus> = _recyclerLoadingStatus

    private val _searchQueryText = MutableLiveData("beşiktaş")
    val searchQueryText: LiveData<String> = _searchQueryText

    private val _articles = MutableLiveData<MutableList<Article>>(mutableListOf())
    val articles: LiveData<MutableList<Article>> = _articles

    var currentPage: Int = 0

    var totalResults: Int = -1;


    init {
        searchNews()
    }


    fun clearData() {
        currentPage = 0
        totalResults = -1
        _articles.postValue(mutableListOf())
        _recyclerLoadingStatus.postValue(RecyclerLoadingStatus.PARTIALLY_LOADED)
    }

    fun searchNews(query: String = _searchQueryText.value ?: "") {
        _searchQueryText.value = query
        viewModelScope.launch(Dispatchers.IO) {

            _recyclerLoadingStatus.postValue(RecyclerLoadingStatus.LOADING)

            when (val searchArticlesResponse =
                newsRepository.searchNews(_searchQueryText.value ?: "", page = currentPage + 1)) {
                is ApiSuccessResponse -> {
                    currentPage++
                    searchArticlesResponse.body.articles?.let {
                        _articles.postValue(_articles.value?.apply { addAll(it) })
                    }
                    totalResults = searchArticlesResponse.body.totalResults

                    when {
                        totalResults == 0 -> _recyclerLoadingStatus.postValue(RecyclerLoadingStatus.EMPTY_CLICK_TO_RELOAD)
                        _articles.value?.size == totalResults -> _recyclerLoadingStatus.postValue(
                            RecyclerLoadingStatus.ALL_LOADED
                        )
                        else -> _recyclerLoadingStatus.postValue(RecyclerLoadingStatus.PARTIALLY_LOADED)
                    }
                }
                else -> {
                    _recyclerLoadingStatus.postValue(RecyclerLoadingStatus.LOADING_ERROR)
                }
            }
        }
    }


    enum class RecyclerLoadingStatus(val value: Int) {
        PARTIALLY_LOADED(1001),
        ALL_LOADED(1002),
        LOADING(1003),
        EMPTY_CLICK_TO_RELOAD(1004),
        LOADING_ERROR(1005)
    }

}