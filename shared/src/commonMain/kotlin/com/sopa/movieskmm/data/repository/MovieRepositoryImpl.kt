package com.sopa.movieskmm.data.repository

import co.touchlab.kermit.Logger
import com.sopa.movieskmm.data.local.MovieLocalDataSource
import com.sopa.movieskmm.data.remote.MovieRemoteDataSource
import com.sopa.movieskmm.domain.model.Movie
import com.sopa.movieskmm.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.sopa.movieskmm.utils.Result
import kotlin.time.ExperimentalTime


class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource? = null,
    private val localDataSource: MovieLocalDataSource? = null,
    private val logger: Logger? = null
): MovieRepository, KoinComponent {
    private val injectedRemoteDataSource: MovieRemoteDataSource by inject()
    private val injectedLocalDataSource: MovieLocalDataSource by inject()
    private val injectedLogger: Logger by inject()

    @OptIn(ExperimentalTime::class)
    override fun getTrendingMovies(): Flow<Result<List<Movie>>> = flow {
        val (cachedMovies, cachedTime) = injectedLocalDataSource.getCachedTrendingMovies()
        if (cachedMovies != null && injectedLocalDataSource.isCacheValid(cachedTime)) {
            injectedLogger.d { "Returning cached trending movies" }
            emit(Result.Success(cachedMovies))
        } else {
            try {
                injectedLogger.d { "Fetching trending movies from API" }
                val response = injectedRemoteDataSource.getTrendingMovies()
                injectedLocalDataSource.cacheTrendingMovies(response?.results)
                emit(Result.Success(response?.results ?: emptyList()))
            } catch (e: Exception) {
                injectedLogger.e { "Error fetching trending movies: $e" }
                if (cachedMovies != null) {
                    injectedLogger.d { "Falling back to cached data" }
                    emit(Result.Success(cachedMovies))
                } else {
                    emit(Result.Failure(Exception("Failed to fetch trending movies, no cache available")))
                }
            }
        }
    }

    override fun searchMovies(query: String): Flow<Result<List<Movie>>> = flow {
        try {
            val response = injectedRemoteDataSource.searchMovies(query)
            injectedLogger.d { "Searched for '$query', found ${response.results.size} movies" }
            emit(Result.Success(response.results))
        } catch (e: Exception) {
            injectedLogger.e { "Error searching movies: $e" }
            emit(Result.Failure(Exception("Failed to search movies")))
        }
    }

    @OptIn(ExperimentalTime::class)
    override fun getMovieDetails(movieId: Int): Flow<Result<MovieDetails>> = flow {
        val (cachedDetails, cachedTime) = injectedLocalDataSource.getCachedMovieDetails(movieId)
        if (cachedDetails != null && injectedLocalDataSource.isCacheValid(cachedTime)) {
            injectedLogger.d { "Returning cached movie details for ID $movieId" }
            emit(Result.Success(cachedDetails))
        } else {
            try {
                injectedLogger.d { "Fetching movie details for ID $movieId from API" }
                val details = injectedRemoteDataSource.getMovieDetails(movieId)
                injectedLocalDataSource.cacheMovieDetails(movieId, details)
                emit(Result.Success(details))
            } catch (e: Exception) {
                injectedLogger.e { "Error fetching movie details for ID $movieId: $e" }
                if (cachedDetails != null) {
                    injectedLogger.d { "Falling back to cached data" }
                    emit(Result.Success(cachedDetails))
                } else {
                    emit(Result.Failure(Exception("Failed to fetch movie details, no cache available")))
                }
            }
        }
    }
}