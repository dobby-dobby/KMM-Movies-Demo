package com.sopa.movieskmm.data.repository

import com.sopa.movieskmm.domain.model.Movie
import com.sopa.movieskmm.domain.model.MovieDetails
import com.sopa.movieskmm.utils.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getTrendingMovies(): Flow<Result<List<Movie>>>
    fun searchMovies(query: String): Flow<Result<List<Movie>>>
    fun getMovieDetails(movieId: Int): Flow<Result<MovieDetails>>
}