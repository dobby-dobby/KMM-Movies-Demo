package com.sopa.movieskmm.data.remote

import com.sopa.movieskmm.domain.model.MovieDetails
import com.sopa.movieskmm.domain.model.TrendingMoviesResponse

interface MovieRemoteDataSource {
    suspend fun getTrendingMovies(): TrendingMoviesResponse?
    suspend fun searchMovies(query: String): TrendingMoviesResponse
    suspend fun getMovieDetails(movieId: Int): MovieDetails
}