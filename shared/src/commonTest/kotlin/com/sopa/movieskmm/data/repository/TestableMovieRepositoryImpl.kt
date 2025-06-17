package com.sopa.movieskmm.data.repository

import com.sopa.movieskmm.data.local.MovieLocalDataSource
import com.sopa.movieskmm.data.remote.MovieRemoteDataSource
import com.sopa.movieskmm.domain.model.Movie
import com.sopa.movieskmm.domain.model.MovieDetails
import com.sopa.movieskmm.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch

class TestableMovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource,
    private val logger: TestLogger
) : MovieRepository {

    override fun getTrendingMovies(): Flow<Result<List<Movie>>> = flow<Result<List<Movie>>> {
        val (cachedMovies, cachedTime) = localDataSource.getCachedTrendingMovies()
        if (cachedMovies != null && localDataSource.isCacheValid(cachedTime)) {
            logger.d { "Returning cached trending movies" }
            emit(Result.Success(cachedMovies))
        } else {
            logger.d { "Fetching trending movies from API" }
            val response = remoteDataSource.getTrendingMovies()
            localDataSource.cacheTrendingMovies(response?.results)
            emit(Result.Success(response?.results ?: emptyList()))
        }
    }.catch { e ->
        logger.e { "Error fetching trending movies: $e" }
        val (cachedMovies, _) = localDataSource.getCachedTrendingMovies()
        if (cachedMovies != null) {
            logger.d { "Falling back to cached data" }
            emit(Result.Success(cachedMovies))
        } else {
            emit(Result.Failure(Exception("Failed to fetch trending movies, no cache available")))
        }
    }

    override fun searchMovies(query: String): Flow<Result<List<Movie>>> = flow<Result<List<Movie>>> {
        val response = remoteDataSource.searchMovies(query)
        logger.d { "Searched for '$query', found ${response.results.size} movies" }
        emit(Result.Success(response.results))
    }.catch { e ->
        logger.e { "Error searching movies: $e" }
        emit(Result.Failure(Exception("Failed to search movies")))
    }

    override fun getMovieDetails(movieId: Int): Flow<Result<MovieDetails>> = flow<Result<MovieDetails>> {
        val (cachedDetails, cachedTime) = localDataSource.getCachedMovieDetails(movieId)
        if (cachedDetails != null && localDataSource.isCacheValid(cachedTime)) {
            logger.d { "Returning cached movie details for ID $movieId" }
            emit(Result.Success(cachedDetails))
        } else {
            logger.d { "Fetching movie details for ID $movieId from API" }
            val details = remoteDataSource.getMovieDetails(movieId)
            localDataSource.cacheMovieDetails(movieId, details)
            emit(Result.Success(details))
        }
    }.catch { e ->
        logger.e { "Error fetching movie details for ID $movieId: $e" }
        val (cachedDetails, _) = localDataSource.getCachedMovieDetails(movieId)
        if (cachedDetails != null) {
            logger.d { "Falling back to cached data" }
            emit(Result.Success(cachedDetails))
        } else {
            emit(Result.Failure(Exception("Failed to fetch movie details, no cache available")))
        }
    }
} 