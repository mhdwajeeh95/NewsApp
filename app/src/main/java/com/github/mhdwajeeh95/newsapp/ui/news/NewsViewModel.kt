package com.github.mhdwajeeh95.newsapp.ui.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class NewsViewModel @Inject constructor(app: Application) : AndroidViewModel(app) {

    private val _searchQueryText = MutableLiveData<String>().apply {
        value = ""
    }
    val searchQueryText: LiveData<String> = _searchQueryText
}