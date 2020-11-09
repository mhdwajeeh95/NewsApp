package com.github.mhdwajeeh95.newsapp.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mhdwajeeh95.newsapp.models.Article
import com.github.mhdwajeeh95.newsapp.repository.NewsRepository
import com.github.mhdwajeeh95.newsapp.viewmodel.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    app: Application,
    val newsRepository: NewsRepository
) : AndroidViewModel(app) {


    private val _articles = MutableLiveData<MutableList<Article>>()
    val articles: LiveData<MutableList<Article>> = _articles

    private val _deleteArticleError: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val deleteArticleError: LiveData<Event<Boolean>> = _deleteArticleError

    init {
        getFavoriteArticles()
    }

    fun getFavoriteArticles() {
        viewModelScope.launch(Dispatchers.IO) {

            _articles.postValue(newsRepository.getFavoriteArticles().toMutableList())
        }
    }

    fun removeArticleFromFavorites(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                newsRepository.deleteFavoriteArticle(article)
                _articles.postValue(_articles.value?.apply { remove(article) })
            } catch (ex: Exception) {
                _deleteArticleError.postValue(Event(true))
            }

        }
    }
}