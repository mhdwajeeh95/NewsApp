package com.github.mhdwajeeh95.newsapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Article @JvmOverloads constructor(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long,

    @SerializedName("source")
    @Ignore
    var source: ArticleSource? = null,

    @Expose(serialize = false, deserialize = false)
    @ColumnInfo(name = "sourceId")
    var sourceId: String,

    @Expose(serialize = false, deserialize = false)
    @ColumnInfo(name = "sourceName")
    var sourceName: String,

    @SerializedName("author")
    @ColumnInfo(name = "author")
    var author: String,

    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title: String,

    @SerializedName("description")
    @ColumnInfo(name = "description")
    var description: String,

    @SerializedName("url")
    @ColumnInfo(name = "url")
    var url: String,

    @SerializedName("urlToImage")
    @ColumnInfo(name = "urlToImage")
    var urlToImage: String,

    @SerializedName("publishedAt")
    @ColumnInfo(name = "publishedAt")
    var publishedAt: String,

    @SerializedName("content")
    @ColumnInfo(name = "content")
    var content: String? = null,

    @Expose(serialize = false, deserialize = false)
    @ColumnInfo(name = "isTop")
    var isTop: Boolean = false,

    @Expose(serialize = false, deserialize = false)
    @ColumnInfo(name = "isFav")
    var isFav: Boolean = false

)