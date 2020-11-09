package com.github.mhdwajeeh95.newsapp.db.daos

import androidx.room.*
import com.github.mhdwajeeh95.newsapp.models.Article


@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Query("SELECT * FROM articles WHERE isFav = 1")
    suspend fun getFavoriteArticles(): List<Article>

    @Delete
    suspend fun deleteArticle(article: Article)

}