package com.sopa.movieskmm.android

import android.app.Application
import com.sopa.movieskmm.di.dataModule
import com.sopa.movieskmm.di.networkModule
import com.sopa.movieskmm.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Only start Koin if it's not already started
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidContext(this@TestApplication)
                modules(listOf(platformModule, networkModule, dataModule))
            }
        }
    }
} 