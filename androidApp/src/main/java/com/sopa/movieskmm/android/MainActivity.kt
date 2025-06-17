package com.sopa.movieskmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.sopa.movieskmm.di.dataModule
import com.sopa.movieskmm.di.networkModule
import com.sopa.movieskmm.di.platformModule
import org.koin.core.context.startKoin
import org.koin.core.context.GlobalContext
import org.koin.android.ext.koin.androidContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Only start Koin if it's not already started
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidContext(this@MainActivity)
                modules(listOf(platformModule, networkModule, dataModule))
            }
        }

        setContent {
            MyApplicationTheme {
                Box(Modifier.safeDrawingPadding()){
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        MoviesApp()
                    }
                }
            }
        }
    }
}
