package com.sopa.movieskmm.android

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sopa.movieskmm.di.dataModule
import com.sopa.movieskmm.di.networkModule
import com.sopa.movieskmm.di.platformModule
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
abstract class TestConfig {

    @Before
    fun setUp() {
        try {
            stopKoin()
        } catch (e: Exception) {
        }
        
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
                modules(listOf(platformModule, networkModule, dataModule))
            }
        }
    }

    companion object {
        const val APP_LOAD_TIMEOUT = 5000L
        const val MOVIES_LOAD_TIMEOUT = 10000L
        const val DETAIL_LOAD_TIMEOUT = 5000L
        const val NAVIGATION_TIMEOUT = 3000L
        const val SEARCH_TIMEOUT = 5000L
        
        const val SEARCH_PLACEHOLDER = "Search Movies"
        const val YEAR_LABEL = "Year:"
        const val VOTE_AVERAGE_LABEL = "Vote Average:"
        const val RELEASE_YEAR_LABEL = "Release Year:"
        const val RUNTIME_LABEL = "Runtime:"
        const val GENRES_LABEL = "Genres:"
        const val OVERVIEW_LABEL = "Overview:"
        const val RATING_LABEL = "Rating:"
        const val HOMEPAGE_LABEL = "Homepage"
        const val ERROR_PREFIX = "Error:"
        const val LOADING_DESCRIPTION = "Loading"
        const val MOVIE_POSTER_DESCRIPTION = "Movie poster"
        
        fun getTargetContext() = InstrumentationRegistry.getInstrumentation().targetContext
        fun getInstrumentationContext() = InstrumentationRegistry.getInstrumentation().context
        
        fun getDeviceInfo(): String {
            val context = getTargetContext()
            val isEmulator = context.packageManager.hasSystemFeature("android.hardware.type.phone")
            return "Device: ${android.os.Build.MODEL}, " +
                    "API: ${android.os.Build.VERSION.SDK_INT}, " +
                    "Emulator: ${isEmulator}"
        }
    }
}

fun createTestComposeRule() = createAndroidComposeRule<MainActivity>() 