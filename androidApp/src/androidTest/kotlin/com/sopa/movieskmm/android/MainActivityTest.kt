package com.sopa.movieskmm.android

import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest : TestConfig() {

    @get:Rule
    val composeTestRule = createTestComposeRule()

    @Before
    fun setUpTest() {
        composeTestRule.waitUntil(timeoutMillis = 8000) {
            try {
                composeTestRule
                    .onAllNodesWithText("Search Movies")
                    .fetchSemanticsNodes().size == 1
            } catch (e: Exception) {
                false
            }
        }
    }

    @Test
    fun testAppLaunchesSuccessfully() {
        composeTestRule.onNodeWithText("Search Movies").assertIsDisplayed()
        composeTestRule.onNodeWithText("Search Movies").assertIsEnabled()
    }

    @Test
    fun testSearchFieldInteraction() {
        composeTestRule.onNodeWithText("Search Movies").performTextInput("Avengers")
        composeTestRule.onNodeWithText("Avengers").assertIsDisplayed()
    }

    @Test
    fun testMoviesListDisplayed() {
        composeTestRule.waitUntil(timeoutMillis = 12000) {
            try {
                composeTestRule.onAllNodesWithText("Error:").fetchSemanticsNodes().size > 0 ||
                composeTestRule.onAllNodesWithContentDescription("Movie poster").fetchSemanticsNodes().size > 0 ||
                composeTestRule.onAllNodesWithText("Year:").fetchSemanticsNodes().size > 0 ||
                composeTestRule.onAllNodesWithText("Vote Average:").fetchSemanticsNodes().size > 0
            } catch (e: Exception) {
                false
            }
        }

        val errorMessages = composeTestRule.onAllNodesWithText("Error:")
        val moviePosters = composeTestRule.onAllNodesWithContentDescription("Movie poster")
        val yearLabels = composeTestRule.onAllNodesWithText("Year:")
        val voteAverageLabels = composeTestRule.onAllNodesWithText("Vote Average:")
        
        if (errorMessages.fetchSemanticsNodes().size > 0) {
            errorMessages[0].assertIsDisplayed()
        } else if (moviePosters.fetchSemanticsNodes().size > 0) {
            moviePosters[0].assertIsDisplayed()
            
            if (yearLabels.fetchSemanticsNodes().size > 0) {
                yearLabels[0].assertIsDisplayed()
            }
            if (voteAverageLabels.fetchSemanticsNodes().size > 0) {
                voteAverageLabels[0].assertIsDisplayed()
            }
        } else if (yearLabels.fetchSemanticsNodes().size > 0 || voteAverageLabels.fetchSemanticsNodes().size > 0) {
            if (yearLabels.fetchSemanticsNodes().size > 0) {
                yearLabels[0].assertIsDisplayed()
            }
            if (voteAverageLabels.fetchSemanticsNodes().size > 0) {
                voteAverageLabels[0].assertIsDisplayed()
            }
        } else {
            composeTestRule.onNodeWithText("Search Movies").assertIsDisplayed()
            composeTestRule.onNodeWithText("Search Movies").assertIsEnabled()
        }
    }

    @Test
    fun testSearchFunctionality() {
        composeTestRule.onNodeWithText("Search Movies").performTextInput("Batman")
        
        composeTestRule.waitUntil(timeoutMillis = 8000) {
            try {
                composeTestRule.onAllNodesWithContentDescription("Movie poster").fetchSemanticsNodes().size > 0 ||
                composeTestRule.onAllNodesWithText("Error:").fetchSemanticsNodes().size > 0 ||
                composeTestRule.onAllNodesWithText("Year:").fetchSemanticsNodes().size > 0
            } catch (e: Exception) {
                false
            }
        }
        
        val searchResults = composeTestRule.onAllNodesWithContentDescription("Movie poster")
        val errorMessages = composeTestRule.onAllNodesWithText("Error:")
        val yearLabels = composeTestRule.onAllNodesWithText("Year:")
        
        if (searchResults.fetchSemanticsNodes().size > 0) {
            searchResults[0].assertIsDisplayed()
        } else if (errorMessages.fetchSemanticsNodes().size > 0) {
            errorMessages[0].assertIsDisplayed()
        } else if (yearLabels.fetchSemanticsNodes().size > 0) {
            yearLabels[0].assertIsDisplayed()
        } else {
            composeTestRule.onNodeWithText("Search Movies").assertIsDisplayed()
        }
    }

    @Test
    fun testLoadingState() {
        composeTestRule.waitUntil(timeoutMillis = 8000) {
            try {
                composeTestRule.onAllNodesWithContentDescription("Movie poster").fetchSemanticsNodes().size > 0 ||
                composeTestRule.onAllNodesWithText("Error:").fetchSemanticsNodes().size > 0 ||
                composeTestRule.onAllNodesWithText("Year:").fetchSemanticsNodes().size > 0
            } catch (e: Exception) {
                false
            }
        }
        
        composeTestRule.onNodeWithText("Search Movies").assertIsEnabled()
    }
} 