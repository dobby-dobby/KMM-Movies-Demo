package com.sopa.movieskmm.data.repository

import co.touchlab.kermit.Logger
import com.sopa.movieskmm.data.local.MovieLocalDataSource
import com.sopa.movieskmm.data.remote.MovieRemoteDataSource
import com.sopa.movieskmm.domain.model.Movie
import com.sopa.movieskmm.domain.model.MovieDetails
import com.sopa.movieskmm.domain.model.TrendingMoviesResponse
import com.sopa.movieskmm.utils.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

// Logger interface for testing
interface TestLogger {
    fun v(message: () -> String)
    fun d(message: () -> String)
    fun i(message: () -> String)
    fun w(message: () -> String)
    fun e(message: () -> String)
    fun wtf(message: () -> String)
}

class MovieRepositoryImplTest {
    
    @Test
    fun `getTrendingMovies should return cached data when cache is valid`() = runTest {
        // Given
        val cachedMovies = listOf(
            Movie(id = 1, title = "Test Movie 1"),
            Movie(id = 2, title = "Test Movie 2")
        )
        val mockLocalDataSource = MockMovieLocalDataSource(
            cachedMovies = cachedMovies,
            cacheTime = Clock.System.now()
        )
        val mockRemoteDataSource = MockMovieRemoteDataSource()
        val mockLogger = MockLogger()
        val repository = TestableMovieRepositoryImpl(
            remoteDataSource = mockRemoteDataSource,
            localDataSource = mockLocalDataSource,
            logger = mockLogger
        )
        
        // When
        val result = repository.getTrendingMovies().take(1).first()
        
        // Then
        assertTrue(result is Result.Success)
        assertEquals(cachedMovies, (result as Result.Success).data)
    }
    
    @Test
    fun `getTrendingMovies should fetch from remote when cache is invalid`() = runTest {
        // Given
        val remoteMovies = listOf(
            Movie(id = 3, title = "Remote Movie 1"),
            Movie(id = 4, title = "Remote Movie 2")
        )
        val mockLocalDataSource = MockMovieLocalDataSource(
            cachedMovies = null,
            cacheTime = Instant.fromEpochMilliseconds(0)
        )
        val mockRemoteDataSource = MockMovieRemoteDataSource(
            trendingResponse = TrendingMoviesResponse(results = remoteMovies)
        )
        val mockLogger = MockLogger()
        val repository = TestableMovieRepositoryImpl(
            remoteDataSource = mockRemoteDataSource,
            localDataSource = mockLocalDataSource,
            logger = mockLogger
        )
        
        // When
        val result = repository.getTrendingMovies().take(1).first()
        
        // Then
        assertTrue(result is Result.Success)
        assertEquals(remoteMovies, (result as Result.Success).data)
    }
    
    @Test
    fun `searchMovies should return search results`() = runTest {
        // Given
        val searchResults = listOf(
            Movie(id = 5, title = "Search Result 1"),
            Movie(id = 6, title = "Search Result 2")
        )
        val mockRemoteDataSource = MockMovieRemoteDataSource(
            searchResponse = TrendingMoviesResponse(results = searchResults)
        )
        val mockLogger = MockLogger()
        val repository = TestableMovieRepositoryImpl(
            localDataSource = MockMovieLocalDataSource(),
            remoteDataSource = mockRemoteDataSource,
            logger = mockLogger
        )
        
        // When
        val result = repository.searchMovies("test query").take(1).first()
        
        // Then
        assertTrue(result is Result.Success)
        assertEquals(searchResults, (result as Result.Success).data)
    }
}

// Mock classes for testing
class MockMovieLocalDataSource(
    private val cachedMovies: List<Movie>? = null,
    private val cacheTime: Instant? = Clock.System.now()
) : MovieLocalDataSource {
    override fun getCachedTrendingMovies(): Pair<List<Movie>?, Instant?> {
        return Pair(cachedMovies, cacheTime)
    }
    
    override fun cacheTrendingMovies(movies: List<Movie>?) {
        // Mock implementation
    }
    
    override fun getCachedMovieDetails(movieId: Int): Pair<MovieDetails?, Instant?> {
        return Pair(null, cacheTime)
    }
    
    override fun cacheMovieDetails(movieId: Int, details: MovieDetails) {
        // Mock implementation
    }
    
    override fun isCacheValid(cachedTime: Instant?): Boolean {
        if (cachedTime == null) return false
        val currentTime = Clock.System.now()
        return (currentTime.toEpochMilliseconds() - cachedTime.toEpochMilliseconds()) < 3600000 // 1 hour cache
    }
}

class MockMovieRemoteDataSource(
    private val trendingResponse: TrendingMoviesResponse? = null,
    private val searchResponse: TrendingMoviesResponse = TrendingMoviesResponse(),
    private val shouldThrowError: Boolean = false
) : MovieRemoteDataSource {
    override suspend fun getTrendingMovies(): TrendingMoviesResponse? {
        if (shouldThrowError) throw Exception("Network error")
        return trendingResponse
    }
    
    override suspend fun searchMovies(query: String): TrendingMoviesResponse {
        if (shouldThrowError) throw Exception("Network error")
        return searchResponse
    }
    
    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        if (shouldThrowError) throw Exception("Network error")
        return MovieDetails(id = movieId, title = "Test Movie")
    }
}

class MockLogger : TestLogger {
    override fun v(message: () -> String) {}
    override fun d(message: () -> String) {}
    override fun i(message: () -> String) {}
    override fun w(message: () -> String) {}
    override fun e(message: () -> String) {}
    override fun wtf(message: () -> String) {}
}