package com.sopa.movieskmm.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MovieTest {
    
    @Test
    fun `getPosterUrl should return correct URL when posterPath exists`() {
        // Given
        val movie = Movie(
            id = 1,
            title = "Test Movie",
            posterPath = "/test-poster.jpg"
        )
        
        // When
        val posterUrl = movie.getPosterUrl()
        
        // Then
        assertEquals("https://image.tmdb.org/t/p/w500/test-poster.jpg", posterUrl)
    }
    
    @Test
    fun `getPosterUrl should return null when posterPath is null`() {
        // Given
        val movie = Movie(
            id = 1,
            title = "Test Movie",
            posterPath = null
        )
        
        // When
        val posterUrl = movie.getPosterUrl()
        
        // Then
        assertNull(posterUrl)
    }
    
    @Test
    fun `getPosterUrl should use custom size when provided`() {
        // Given
        val movie = Movie(
            id = 1,
            title = "Test Movie",
            posterPath = "/test-poster.jpg"
        )
        
        // When
        val posterUrl = movie.getPosterUrl("w780")
        
        // Then
        assertEquals("https://image.tmdb.org/t/p/w780/test-poster.jpg", posterUrl)
    }
    
    @Test
    fun `getReleaseYear should return year when releaseDate is valid`() {
        // Given
        val movie = Movie(
            id = 1,
            title = "Test Movie",
            releaseDate = "2023-12-25"
        )
        
        // When
        val releaseYear = movie.getReleaseYear()
        
        // Then
        assertEquals(2023, releaseYear)
    }
    
    @Test
    fun `getReleaseYear should return null when releaseDate is null`() {
        // Given
        val movie = Movie(
            id = 1,
            title = "Test Movie",
            releaseDate = null
        )
        
        // When
        val releaseYear = movie.getReleaseYear()
        
        // Then
        assertNull(releaseYear)
    }
    
    @Test
    fun `getReleaseYear should return null when releaseDate is invalid`() {
        // Given
        val movie = Movie(
            id = 1,
            title = "Test Movie",
            releaseDate = "invalid-date"
        )
        
        // When
        val releaseYear = movie.getReleaseYear()
        
        // Then
        assertNull(releaseYear)
    }
    
    @Test
    fun `Movie should have correct default values`() {
        // Given & When
        val movie = Movie()
        
        // Then
        assertEquals(0, movie.id)
        assertEquals("", movie.title)
        assertEquals("", movie.originalTitle)
        assertEquals("movie", movie.mediaType)
        assertEquals(false, movie.adult)
        assertEquals("en", movie.originalLanguage)
        assertEquals(emptyList<Int>(), movie.genreIds)
        assertEquals(0f, movie.popularity)
        assertEquals(false, movie.video)
        assertEquals(0f, movie.voteAverage)
        assertEquals(0, movie.voteCount)
    }
} 