package com.sopa.movieskmm.android.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.sopa.movieskmm.android.viewmodel.MoviesViewModel

@Composable
fun MoviesScreen(navController: NavController, viewModel: MoviesViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { newQuery ->
                searchQuery = newQuery
                viewModel.updateSearchQuery(newQuery)
            },
            label = { Text("Search Movies") },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Text(text = "Error: ${uiState.error}")
        } else {
            LazyColumn {
                items(uiState.movies) { movie ->
                    Row (
                        modifier = Modifier.clickable { navController.navigate("details/${movie.id}") }
                    ){
                        Box(
                            modifier = Modifier
                                .weight(3f)
                                .padding(8.dp)
                        ) {
                            AsyncImage(
                                model = movie.getPosterUrl(),
                                contentDescription = "Movie poster"
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(7f)
                                .padding(8.dp)
                        ) {
                            Column {
                                Text(
                                    text = movie.title,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Year: ${movie.getReleaseYear()}",
                                    modifier = Modifier.padding(bottom = 2.dp)
                                )
                                Text(
                                    text = "Vote Average: ${movie.voteAverage}",
                                    modifier = Modifier.padding(bottom = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}