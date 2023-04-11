package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.ui.screens.search.SearchType
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HomeScreenUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Composable
    private fun HomeScreenUIContent(
        selectedActor: (actorId: Int) -> Unit = { },
        selectedMovie: (movieId: Int) -> Unit = { },
        favoriteMovies: List<Movie> = emptyList(),
        updateSearchType: (searchType: SearchType) -> Unit = {  },
        homeSheetUIState: HomeSheetUIState = HomeSheetUIState(),
        homeUIState: HomeUIState = HomeUIState()
    ) {
        HomeScreenUI(
            selectedActor = selectedActor,
            selectedMovie = selectedMovie,
            homeSheetUIState = homeSheetUIState,
            favoriteMovies = favoriteMovies,
            updateSearchType = updateSearchType,
            homeUIState = homeUIState,
        )
    }

    @Test
    fun assertHomeTabsAreDisplayedAndClickable() {
        composeTestRule.setContent {
            HomeScreenUIContent()
        }

        composeTestRule
            .onNodeWithText(text = "Actors")
            .assertIsDisplayed()
            .performClick()

        composeTestRule
            .onNodeWithText(text = "Movies")
            .assertIsDisplayed()
            .performClick()

        composeTestRule
            .onNodeWithText(text = "TV Shows")
            .assertIsDisplayed()
            .performClick()
    }
}