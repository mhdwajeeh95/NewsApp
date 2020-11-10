package com.github.mhdwajeeh95.newsapp.models

import android.os.Bundle
import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "articles",
    indices = [Index(value = ["url"], unique = true)]
)
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

) {

    constructor(bundle: Bundle) : this(
        bundle.getLong("id"),
        ArticleSource(bundle.getString("sourceId", ""), bundle.getString("sourceName", "")),
        bundle.getString("sourceId", ""),
        bundle.getString("sourceName", ""),
        bundle.getString("author", ""),
        bundle.getString("title", ""),
        bundle.getString("description", ""),
        bundle.getString("url", ""),
        bundle.getString("urlToImage", ""),
        bundle.getString("publishedAt", ""),
        bundle.getString("content"),
        bundle.getBoolean("isTop"),
        bundle.getBoolean("isFav")
    )


    fun toBundle(): Bundle {

        val bundle = Bundle()

        bundle.putLong("id", this.id)

        if (this.source == null) {
            bundle.putString("sourceId", this.sourceId)
            bundle.putString("sourceName", this.sourceName)

        } else {
            bundle.putString("sourceId", this.source!!.id)
            bundle.putString("sourceName", this.source!!.name)
        }

        bundle.putString("author", this.author)
        bundle.putString("title", this.title)
        bundle.putString("description", this.description)
        bundle.putString("url", this.url)
        bundle.putString("urlToImage", this.urlToImage)
        bundle.putString("publishedAt", this.publishedAt)
        bundle.putString("content", this.content)
        bundle.putBoolean("isTop", this.isTop)
        bundle.putBoolean("isFav", this.isFav)

        return bundle
    }

}