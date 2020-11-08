package com.github.mhdwajeeh95.newsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.mhdwajeeh95.newsapp.db.daos.ArticleDao
import com.github.mhdwajeeh95.newsapp.models.Article
import com.github.mhdwajeeh95.newsapp.models.ArticleSource

@Database(
    entities = [
        Article::class,
        ArticleSource::class
    ],
    version = DbConst.DATABASE_VERSION,
    exportSchema = false
)
abstract class AppRoomDatabase() : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

}