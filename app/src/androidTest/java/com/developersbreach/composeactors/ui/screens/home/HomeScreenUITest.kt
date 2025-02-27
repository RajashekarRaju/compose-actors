package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.developersbreach.composeactors.data.datasource.fake.nowPlayingMoviesList
import com.developersbreach.composeactors.data.datasource.fake.popularPersonLists
import com.developersbreach.composeactors.data.datasource.fake.trendingPersonLists
import com.developersbreach.composeactors.data.datasource.fake.upcomingMoviesList
import com.developersbreach.composeactors.ui.screens.search.SearchType
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockHomeUIState: HomeData = HomeData(
        popularPersonList = popularPersonLists,
        trendingPersonList = trendingPersonLists,
        isFetchingPersons = false,
        upcomingMoviesList = upcomingMoviesList,
        nowPlayingMoviesList = flowOf(PagingData.from(nowPlayingMoviesList))
    )

    @Composable
    private fun HomeScreenUIContent(
        navigateToSelectedPerson: (personId: Int) -> Unit = { },
        navigateToSelectedMovie: (movieId: Int) -> Unit = { },
        navigateToFavorite: () -> Unit = { },
        navigateToSearch: (searchType: SearchType) -> Unit = { },
        updateHomeSearchType: (searchType: SearchType) -> Unit = { },
        navigateToAbout: () -> Unit = { },
        navigateToSearchBySearchType: SearchType = SearchType.Persons,
        homeSheetUIState: HomeSheetUIState = HomeSheetUIState(),
        data: HomeData = mockHomeUIState
    ) {
        HomeScreenUI(
            modifier = Modifier,
            navigateToSelectedPerson = navigateToSelectedPerson,
            navigateToSelectedMovie = navigateToSelectedMovie,
            navigateToFavorite = navigateToFavorite,
            navigateToSearch = navigateToSearch,
            navigateToAbout = navigateToAbout,
            navigateToSearchBySearchType = navigateToSearchBySearchType,
            data = data,
            sheetUiState = homeSheetUIState,
            updateHomeSearchType = updateHomeSearchType
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

    @Test
    fun assertPopularAndTrendingActorsItemsAreVisibleInHomeScreen() {
        composeTestRule.setContent {
            HomeScreenUIContent()
        }

        composeTestRule
            .onNodeWithText(text = "Monica Bellucci")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(text = "Daniel Craig")
            .assertIsDisplayed()
    }

    @Test
    fun onClickAnyActorItem_InActorsTab_shouldNavigateToActorDetailsScreen() {
        composeTestRule.setContent {
            HomeScreenUIContent()
        }

        // We need to be in actors tab for actors list to appear and interact
        composeTestRule
            .onNodeWithText(text = "Actors")
            .assertIsDisplayed()
            .performClick()

        // Click actor with name on home screen
        composeTestRule
            .onNodeWithText(text = "Monica Bellucci")
            .assertIsDisplayed()
            .performClick()

        // If actors name visible in details screen, we have navigated to details screen
        composeTestRule
            .onNodeWithText("Monica Bellucci")
            .assertIsDisplayed()
    }

    @Test
    fun onClickAnyMovieItem_InMoviesTab_shouldNavigateToMovieDetailsScreen() {
        composeTestRule.setContent {
            HomeScreenUIContent()
        }

        // We need to be in movies tab for movies to appear and interact
        composeTestRule
            .onNodeWithText(text = "Movies")
            .assertIsDisplayed()
            .performClick()

        // Click movie with name on home screen
        composeTestRule
            .onNodeWithContentDescription(label = "Oppenheimer")
            .assertIsDisplayed()
            .performClick()
    }

    @Test
    fun onClickSearchTopAppBar_shouldNavigateToSearchScreen() {
        composeTestRule.setContent {
            HomeScreenUIContent(
                navigateToSearch = { SearchType.Persons }
            )
        }

        // We need to be in actors tab for actors list to appear and interact
        composeTestRule
            .onNodeWithText(text = "Actors")
            .assertIsDisplayed()
            .performClick()

        composeTestRule
            .onNodeWithTag(testTag = "TestTag:HomeTopAppBar")
            .assertIsDisplayed()
            .performClick()
    }
}