package com.sopa.movieskmm.di

import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

val platformModule = module {
    single { Darwin.create() }
} 