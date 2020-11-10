package com.github.mhdwajeeh95.newsapp.di

import android.app.Application
import com.github.mhdwajeeh95.newsapp.ui.articledetails.ArticleDetailsActivity
import com.github.mhdwajeeh95.newsapp.ui.favorites.FavoritesFragment
import com.github.mhdwajeeh95.newsapp.ui.news.NewsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Dagger Main AppComponent (Application Dependency Graph)
 */

@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    fun inject(newsFragment: NewsFragment)

    fun inject(favoritesFragment: FavoritesFragment)

    fun inject(articleDetailsActivity: ArticleDetailsActivity)

}