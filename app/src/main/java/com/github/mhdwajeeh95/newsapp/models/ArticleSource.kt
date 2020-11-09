package com.github.mhdwajeeh95.newsapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "article_sources")
data class ArticleSource @JvmOverloads constructor(

    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    var name: String,

    @SerializedName("description")
    @ColumnInfo(name = "description")
    var description: String? = null,

    @SerializedName("url")
    @ColumnInfo(name = "url")
    var url: String? = null,

    @SerializedName("category")
    @ColumnInfo(name = "category")
    var category: String? = null,

    @SerializedName("language")
    @ColumnInfo(name = "language")
    var language: String? = null,

    @SerializedName("country")
    @ColumnInfo(name = "country")
    var country: String? = null
)