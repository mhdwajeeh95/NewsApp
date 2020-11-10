package com.github.mhdwajeeh95.newsapp.ui.articledetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mhdwajeeh95.newsapp.models.Article
import com.github.mhdwajeeh95.newsapp.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleDetailsViewModel @Inject constructor(
    app: Application,
    private val newsRepository: NewsRepository
) : AndroidViewModel(app) {

    val article = MutableLiveData(null as Article?)


    fun setArticleFavorite(favorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            article.value?.let { newsRepository.setArticleFavorite(it, favorite) }

            article.value?.let { article.postValue(article.value!!.apply { isFav = favorite })}
        }
    }

}