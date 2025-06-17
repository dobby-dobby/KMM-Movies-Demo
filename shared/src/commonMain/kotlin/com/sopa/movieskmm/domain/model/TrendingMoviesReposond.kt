package com.sopa.movieskmm.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class TrendingMoviesResponse(
    @SerialName("page") val page: Int = 1,
    @SerialName("results") val results: List<Movie> = emptyList(),
    @SerialName("total_pages") val totalPages: Int = 1,
    @SerialName("total_results") val totalResults: Int = 0
)