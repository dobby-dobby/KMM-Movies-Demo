package com.sopa.movieskmm.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("id") val id: Int = 0,
    @SerialName("title") val title: String = "",
    @SerialName("original_title") val originalTitle: String = "",
    @SerialName("overview") val overview: String? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("media_type") val mediaType: String = "movie",
    @SerialName("adult") val adult: Boolean = false,
    @SerialName("original_language") val originalLanguage: String = "en",
    @SerialName("genre_ids") val genreIds: List<Int> = emptyList(),
    @SerialName("popularity") val popularity: Float = 0f,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("video") val video: Boolean = false,
    @SerialName("vote_average") val voteAverage: Float = 0f,
    @SerialName("vote_count") val voteCount: Int = 0
) {
    fun getPosterUrl(size: String = "w500"): String? {
        return posterPath?.let { "https://image.tmdb.org/t/p/$size$it" }
    }

    fun getReleaseYear(): Int? {
        return releaseDate?.let { dateStr ->
            try {
                LocalDate.parse(dateStr).year
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}