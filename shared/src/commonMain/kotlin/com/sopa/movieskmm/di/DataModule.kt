package com.sopa.movieskmm.di

import co.touchlab.kermit.Logger
import com.sopa.movieskmm.data.local.MovieLocalDataSource
import com.sopa.movieskmm.data.local.MovieLocalDataSourceImpl
import com.sopa.movieskmm.data.remote.MovieRemoteDataSource
import com.sopa.movieskmm.data.remote.MovieRemoteDataSourceImpl
import com.sopa.movieskmm.data.repository.MovieRepository
import com.sopa.movieskmm.data.repository.MovieRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    single<MovieRemoteDataSource> { MovieRemoteDataSourceImpl() }
    single<MovieRepository> { MovieRepositoryImpl() }
    single { Logger.withTag("Movies KMM App")}
    single<MovieLocalDataSource> { MovieLocalDataSourceImpl() }
}