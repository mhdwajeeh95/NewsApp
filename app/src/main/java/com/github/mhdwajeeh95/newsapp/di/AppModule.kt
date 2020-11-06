package com.github.mhdwajeeh95.newsapp.di

import android.app.Application
import android.content.Context
import com.github.mhdwajeeh95.newsapp.app.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideMyApplication(application: Application): MyApplication {
        return application as MyApplication
    }

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }


}