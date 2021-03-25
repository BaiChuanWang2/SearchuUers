package com.example.searchusers

import android.app.Application
import com.example.searchusers.di.appModule
import com.example.searchusers.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(appModule, retrofitModule))
        }
    }
}