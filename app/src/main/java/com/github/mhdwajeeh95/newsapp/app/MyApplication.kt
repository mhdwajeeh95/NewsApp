package com.github.mhdwajeeh95.newsapp.app

import android.app.Application
import android.util.Log
import com.github.mhdwajeeh95.newsapp.BuildConfig
import com.github.mhdwajeeh95.newsapp.di.AppComponent
import com.github.mhdwajeeh95.newsapp.di.DaggerAppComponent
import timber.log.Timber

class MyApplication : Application() {

    // Instance of the AppComponent
    val appComponent: AppComponent by lazy {
        initializeDaggerAppComponent()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // init Timber logger
        if (BuildConfig.DEBUG) { // debug mode
            Timber.plant(Timber.DebugTree())
        } else {// release mode
            Timber.plant(object : Timber.Tree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    if (priority < Log.ERROR)
                        return

                    // todo: log your crash to Firebase CrashAnalytics
                    // FirebaseCrash.report(message);
                    // FirebaseCrash.report(new Exception(message));
                }

            })
        }
    }

    fun initializeDaggerAppComponent(): AppComponent {
        return DaggerAppComponent.factory().create(this)
    }

    companion object {
        private lateinit var instance: MyApplication
        fun getInstance() = instance
    }
}