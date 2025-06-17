package com.sopa.movieskmm.data.remote

import com.sopa.movieskmm.data.AppConfig
import com.sopa.movieskmm.domain.model.MovieDetails
import com.sopa.movieskmm.domain.model.TrendingMoviesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MovieRemoteDataSourceImpl : MovieRemoteDataSource, KoinComponent {
    private val client: HttpClient by inject()

    override suspend fun getTrendingMovies(): TrendingMoviesResponse? {
        return client.get("${AppConfig.BASE_URL}3/trending/movie/day") {
            parameter("api_key", AppConfig.API_KEY)
        }.body()
    }

    override suspend fun searchMovies(query: String): TrendingMoviesResponse {
        return client.get("${AppConfig.BASE_URL}3/search/movie") {
            parameter("api_key", AppConfig.API_KEY)
            parameter("query", query)
        }.body()
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return client.get("${AppConfig.BASE_URL}3/movie/$movieId") {
            parameter("api_key", AppConfig.API_KEY)
            parameter("append_to_response", "credits,images,videos") // Optional: fetch additional data
        }.body()
    }
}