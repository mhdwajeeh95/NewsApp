package com.github.mhdwajeeh95.newsapp.app

import android.app.Application
import com.github.mhdwajeeh95.newsapp.di.AppComponent
import com.github.mhdwajeeh95.newsapp.di.DaggerAppComponent

class MyApplication : Application() {

    // Instance of the AppComponent
    val appComponent: AppComponent by lazy {
        initializeDaggerAppComponent()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun initializeDaggerAppComponent(): AppComponent {
        return DaggerAppComponent.factory().create(this)
    }

    companion object {
        private lateinit var instance: MyApplication
        fun getInstance() = instance
    }
}