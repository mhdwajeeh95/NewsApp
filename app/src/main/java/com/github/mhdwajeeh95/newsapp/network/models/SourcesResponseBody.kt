package com.github.mhdwajeeh95.newsapp.network.models

import com.github.mhdwajeeh95.newsapp.models.ArticleSource
import com.github.mhdwajeeh95.newsapp.network.models.base.PagedResponseBody
import com.google.gson.annotations.SerializedName

class SourcesResponseBody : PagedResponseBody() {

    @SerializedName("sources")
    var sources: List<ArticleSource>? = null

}


