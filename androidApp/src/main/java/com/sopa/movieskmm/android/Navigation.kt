package com.sopa.movieskmm.android

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sopa.movieskmm.android.compose.MovieDetailScreen
import com.sopa.movieskmm.android.compose.MoviesScreen

@Composable
fun MoviesApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "movies") {
        composable("movies") { MoviesScreen(navController) }
        composable("details/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull() ?: 0
            MovieDetailScreen(movieId)
        }
    }
}