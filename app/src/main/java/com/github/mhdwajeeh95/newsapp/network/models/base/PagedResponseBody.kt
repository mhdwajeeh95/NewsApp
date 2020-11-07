package com.github.mhdwajeeh95.newsapp.network.models.base

import com.google.gson.annotations.SerializedName

abstract class PagedResponseBody : ApiResponseBody() {

    @SerializedName("totalResults")
    var totalResults: Int = 0

}


