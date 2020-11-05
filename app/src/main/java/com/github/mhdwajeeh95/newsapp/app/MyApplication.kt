package com.github.mhdwajeeh95.newsapp.app

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: MyApplication
        fun getInstance() = instance
    }
}