package com.sopa.movieskmm

import com.sopa.movieskmm.di.dataModule
import com.sopa.movieskmm.di.networkModule
import com.sopa.movieskmm.di.platformModule
import org.koin.core.context.startKoin

fun doInitKoin() {
    startKoin {
        modules(listOf(platformModule, networkModule, dataModule))
    }
} 