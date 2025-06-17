package com.sopa.movieskmm.data.local

import com.sopa.movieskmm.domain.model.Movie
import com.sopa.movieskmm.domain.model.MovieDetails
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class MovieLocalDataSourceImpl : MovieLocalDataSource {
    private val cache = mutableMapOf<String, Pair<List<Movie>, Instant>>()
    private val cacheDuration = 1 * 60 * 60 * 1000L
    private val detailsCache = mutableMapOf<Int, Pair<MovieDetails, Instant>>()

    override fun getCachedTrendingMovies(): Pair<List<Movie>?, Instant?> {
        return cache["trending"] ?: (null to null)
    }

    override fun cacheTrendingMovies(movies: List<Movie>?) {
        cache["trending"] = (movies to Clock.System.now()) as Pair<List<Movie>, Instant>
    }

    override fun getCachedMovieDetails(movieId: Int): Pair<MovieDetails?, Instant?> {
        return detailsCache[movieId] ?: (null to null)
    }

    override fun cacheMovieDetails(movieId: Int, details: MovieDetails) {
        detailsCache[movieId] = details to Clock.System.now()
    }

    override fun isCacheValid(cachedTime: Instant?): Boolean {
        return cachedTime?.let { Clock.System.now().toEpochMilliseconds() - it.toEpochMilliseconds() < cacheDuration } == true
    }
}