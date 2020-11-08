package com.github.mhdwajeeh95.newsapp.di

import android.content.Context
import androidx.room.Room
import com.github.mhdwajeeh95.newsapp.db.AppRoomDatabase
import com.github.mhdwajeeh95.newsapp.db.DbConst
import com.github.mhdwajeeh95.newsapp.db.daos.ArticleDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Singleton
    @Provides
    fun provideDb(context: Context): AppRoomDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppRoomDatabase::class.java,
            DbConst.DATABASE_NAME
        )
            .build()
    }

    @Singleton
    @Provides
    fun provideArticleDao(db: AppRoomDatabase): ArticleDao {
        return db.articleDao()
    }

}