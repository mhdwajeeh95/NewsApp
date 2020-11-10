package com.github.mhdwajeeh95.newsapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.mhdwajeeh95.newsapp.ui.articledetails.ArticleDetailsViewModel
import com.github.mhdwajeeh95.newsapp.ui.favorites.FavoritesViewModel
import com.github.mhdwajeeh95.newsapp.ui.news.NewsViewModel
import com.github.mhdwajeeh95.newsapp.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun bindFavoritesViewModel(viewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    abstract fun bindNewsViewModel(viewModel: NewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArticleDetailsViewModel::class)
    abstract fun bindArticleDetailsViewModel(viewModel: ArticleDetailsViewModel): ViewModel


    /**
     * binds the ViewModelFactory we created [ViewModelFactory] to [ViewModelProvider.Factory]
     */
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
