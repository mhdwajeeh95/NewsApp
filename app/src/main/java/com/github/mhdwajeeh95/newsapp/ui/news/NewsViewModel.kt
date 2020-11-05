package com.github.mhdwajeeh95.newsapp.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsViewModel : ViewModel() {

    private val _searchQueryText = MutableLiveData<String>().apply {
        value = ""
    }
    val searchQueryText: LiveData<String> = _searchQueryText
}