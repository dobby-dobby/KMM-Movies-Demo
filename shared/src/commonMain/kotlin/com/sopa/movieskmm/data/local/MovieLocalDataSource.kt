package com.sopa.movieskmm.data.local

import com.sopa.movieskmm.domain.model.Movie
import com.sopa.movieskmm.domain.model.MovieDetails
import kotlinx.datetime.Instant

interface MovieLocalDataSource {
    fun getCachedTrendingMovies(): Pair<List<Movie>?, Instant?>
    fun cacheTrendingMovies(movies: List<Movie>?)

    fun getCachedMovieDetails(movieId: Int): Pair<MovieDetails?, Instant?>
    fun cacheMovieDetails(movieId: Int, details: MovieDetails)

    fun isCacheValid(cachedTime: Instant?): Boolean
}