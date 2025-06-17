package com.sopa.movieskmm.android.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopa.movieskmm.data.repository.MovieRepository
import com.sopa.movieskmm.domain.model.Movie
import com.sopa.movieskmm.domain.model.MovieDetails
import com.sopa.movieskmm.utils.Result
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class MoviesUiState(
    val movies: List<Movie> = emptyList(),
    val selectedMovieDetails: MovieDetails? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)

class MoviesViewModel : ViewModel(), KoinComponent {
    private val repository: MovieRepository by inject()

    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    private var searchJob: Job? = null

    init {
        loadTrendingMovies()
        observeSearchQuery()
    }

    private fun loadTrendingMovies() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            repository.getTrendingMovies().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            movies = result.data.map { it },
                            isLoading = false,
                            error = null
                        )
                    }

                    is Result.Failure -> {
                        Log.e("Error fetching movies:", result.exception.message.toString())
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.exception.message
                        )
                    }
                }
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, searchQuery = query)
            repository.searchMovies(query).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        val listMoviesSearched = result.data.map { it }
                        _uiState.value = _uiState.value.copy(
                            movies = listMoviesSearched,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Result.Failure -> {
                        Log.e("Error", "Error searching movies for '$query': ${result.exception.message}")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.exception.message ?: "Unknown error"
                        )
                    }
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery.debounce(300)
                .collectLatest { query ->
                    if (query.isNotEmpty()) {
                        searchMovies(query)
                    } else {
                        loadTrendingMovies()
                    }
                }
        }
    }

    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            repository.getMovieDetails(movieId).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            selectedMovieDetails = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Result.Failure -> {
                        Log.e("Error: ", "Error fetching movie details for ID $movieId: ${result.exception.message}")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.exception.message ?: "Unknown error"
                        )
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }

}