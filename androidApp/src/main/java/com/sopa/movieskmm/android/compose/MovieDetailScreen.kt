package com.sopa.movieskmm.android.compose

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.sopa.movieskmm.android.viewmodel.MoviesViewModel

@Composable
fun MovieDetailScreen(
    movieId: Int,
    viewModel: MoviesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(movieId) {
        viewModel.loadMovieDetails(movieId)
    }

    if (uiState.isLoading) {
        CircularProgressIndicator()
    } else if (uiState.error != null) {
        Text(text = "Error: ${uiState.error}")
    } else if (uiState.selectedMovieDetails != null) {
        val details = uiState.selectedMovieDetails!!
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                Text(text = details.title, fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
            }
            item {
                details.getPosterUrl()?.let { url ->
                    AsyncImage(
                        model = details.getPosterUrl(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                    )
                }
            }
            item {
                details.getReleaseYear()?.let { year ->
                    Text(
                        text = "Release Year: $year",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            item {
                details.runtime?.let { runtime ->
                    Text(text = "Runtime: $runtime minutes", fontSize = 16.sp)
                }
            }
            item {
                details.overview?.let { overview ->
                    Text(
                        text = "Overview: $overview",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            item {
                details.genres?.joinToString { it.name }?.let { genres ->
                    Text(
                        text = "Genres: $genres",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            item {
                details.voteAverage?.let { vote ->
                    Text(
                        text = "Rating: $vote/10",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            item {
                details.homepage?.let { homepage ->
                    Text(
                        text = "Homepage",
                        fontSize = 14.sp,
                        color = androidx.compose.ui.graphics.Color.Blue,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, homepage.toUri())
                                context.startActivity(intent)
                            }
                    )
                }
            }
        }
    }
}