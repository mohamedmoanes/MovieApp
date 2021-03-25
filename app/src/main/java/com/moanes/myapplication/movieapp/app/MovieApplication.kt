package com.moanes.myapplication.movieapp.app

import android.app.Application
import com.facebook.stetho.Stetho
import com.moanes.myapplication.movieapp.module.appModules
import com.moanes.myapplication.movieapp.module.repoModule
import com.moanes.myapplication.movieapp.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

        startKoin {
            androidContext(this@MovieApplication)
            modules(listOf(appModules, repoModule, viewModelModule))
        }

    }
}