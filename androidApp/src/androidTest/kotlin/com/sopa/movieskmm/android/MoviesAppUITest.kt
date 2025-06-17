package com.sopa.movieskmm.android

import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoviesAppUITest : TestConfig() {

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
    fun testAppResponsiveness() {
        composeTestRule.onNodeWithText("Search Movies").assertIsEnabled()
        
        composeTestRule.onNodeWithText("Search Movies").performTextInput("Test")
        composeTestRule.onNodeWithText("Test").assertIsDisplayed()
        
        composeTestRule.onNodeWithText("Test").performTextClearance()
        
        composeTestRule.onNodeWithText("Search Movies").assertIsDisplayed()
    }

    @Test
    fun testErrorHandling() {
        composeTestRule.waitUntil(timeoutMillis = 12000) {
            try {
                composeTestRule.onAllNodesWithContentDescription("Movie poster").fetchSemanticsNodes().size > 0 ||
                composeTestRule.onAllNodesWithText("Error:").fetchSemanticsNodes().size > 0 ||
                composeTestRule.onAllNodesWithText("Year:").fetchSemanticsNodes().size > 0
            } catch (e: Exception) {
                false
            }
        }

        val errorMessages = composeTestRule.onAllNodesWithText("Error:")
        if (errorMessages.fetchSemanticsNodes().size > 0) {
            errorMessages[0].assertIsDisplayed()
        }

        composeTestRule.onNodeWithText("Search Movies").assertIsEnabled()
    }

    @Test
    fun testSearchDebouncing() {
        val searchText = "QuickSearchTest"
        
        for (char in searchText) {
            composeTestRule.onNodeWithText("Search Movies").performTextInput(char.toString())
        }
        
        composeTestRule.onNodeWithText(searchText).assertIsDisplayed()
        
        composeTestRule.waitUntil(timeoutMillis = 8000) {
            try {
                composeTestRule.onAllNodesWithContentDescription("Movie poster").fetchSemanticsNodes().size > 0 ||
                composeTestRule.onAllNodesWithText("Error:").fetchSemanticsNodes().size > 0 ||
                composeTestRule.onAllNodesWithText("Year:").fetchSemanticsNodes().size > 0
            } catch (e: Exception) {
                false
            }
        }
    }
} 