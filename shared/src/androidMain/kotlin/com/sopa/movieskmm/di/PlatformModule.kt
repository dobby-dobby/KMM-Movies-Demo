package com.sopa.movieskmm.di

import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

val platformModule = module {
    single { OkHttp.create() }
} 