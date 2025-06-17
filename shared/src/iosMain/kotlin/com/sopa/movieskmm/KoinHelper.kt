package com.sopa.movieskmm

import com.sopa.movieskmm.data.repository.MovieRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinHelper : KoinComponent {
    val movieRepository: MovieRepository by inject()
} 