package com.sopa.movieskmm.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate

@Serializable
data class MovieDetails(
    @SerialName("adult") val adult: Boolean = false,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("belongs_to_collection") val belongsToCollection: BelongsToCollection? = null,
    @SerialName("budget") val budget: Int? = null,
    @SerialName("genres") val genres: List<Genre>? = null,
    @SerialName("homepage") val homepage: String? = null,
    @SerialName("id") val id: Int = 0,
    @SerialName("imdb_id") val imdbId: String? = null,
    @SerialName("origin_country") val originCountry: List<String>? = null,
    @SerialName("original_language") val originalLanguage: String? = null,
    @SerialName("original_title") val originalTitle: String? = null,
    @SerialName("overview") val overview: String? = null,
    @SerialName("popularity") val popularity: Double? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("production_companies") val productionCompanies: List<ProductionCompany>? = null,
    @SerialName("production_countries") val productionCountries: List<ProductionCountry>? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("revenue") val revenue: Long? = null,
    @SerialName("runtime") val runtime: Int? = null,
    @SerialName("spoken_languages") val spokenLanguages: List<SpokenLanguage>? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("tagline") val tagline: String? = null,
    @SerialName("title") val title: String = "",
    @SerialName("video") val video: Boolean = false,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("vote_count") val voteCount: Int? = null
) {
    fun getPosterUrl(size: String = "w500"): String? {
        return posterPath?.let { "https://image.tmdb.org/t/p/$size$it" }
    }

    fun getBackdropUrl(size: String = "w780"): String? {
        return backdropPath?.let { "https://image.tmdb.org/t/p/$size$it" }
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

@Serializable
data class BelongsToCollection(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null
) {
    fun getPosterUrl(size: String = "w500"): String? {
        return posterPath?.let { "https://image.tmdb.org/t/p/$size$it" }
    }

    fun getBackdropUrl(size: String = "w780"): String? {
        return backdropPath?.let { "https://image.tmdb.org/t/p/$size$it" }
    }
}

@Serializable
data class Genre(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)

@Serializable
data class ProductionCompany(
    @SerialName("id") val id: Int,
    @SerialName("logo_path") val logoPath: String? = null,
    @SerialName("name") val name: String,
    @SerialName("origin_country") val originCountry: String
) {
    fun getLogoUrl(size: String = "w185"): String? {
        return logoPath?.let { "https://image.tmdb.org/t/p/$size$it" }
    }
}

@Serializable
data class ProductionCountry(
    @SerialName("iso_3166_1") val iso3166_1: String,
    @SerialName("name") val name: String
)

@Serializable
data class SpokenLanguage(
    @SerialName("english_name") val englishName: String,
    @SerialName("iso_639_1") val iso639_1: String,
    @SerialName("name") val name: String
)