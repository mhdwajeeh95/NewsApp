package com.github.mhdwajeeh95.newsapp.network.models.base

import com.google.gson.annotations.SerializedName


abstract class ApiResponseBody {

    @SerializedName("status")
    var status: String? = null

    // for error responses
    @SerializedName("code")
    var code: String? = null

    // for error responses
    @SerializedName("message")
    var message: String? = null

}


