package com.github.mhdwajeeh95.newsapp.network.models

import com.github.mhdwajeeh95.newsapp.models.Article
import com.github.mhdwajeeh95.newsapp.network.models.base.PagedResponseBody
import com.google.gson.annotations.SerializedName

class ArticlesResponseBody : PagedResponseBody() {

    @SerializedName("articles")
    var articles: List<Article>? = null

}


