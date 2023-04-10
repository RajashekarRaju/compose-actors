package com.developersbreach.composeactors.ui.screens.home.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.components.CategoryTitle
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.screens.home.HomeSheetUIState
import com.developersbreach.composeactors.ui.screens.home.HomeUIState
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme

@Composable
fun MoviesTabContent(
    homeUIState: HomeUIState,
    getSelectedMovieDetails: (Int) -> Unit,
    homeSheetUIState: HomeSheetUIState,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            CategoryTitle(title = "Upcoming", alpha = 0.5f)
            Spacer(modifier = Modifier.height(16.dp))
            UpcomingMovies(homeUIState, getSelectedMovieDetails)
            Spacer(modifier = Modifier.height(28.dp))
            CategoryTitle(title = "Now Playing", alpha = 0.5f)
            Spacer(modifier = Modifier.height(8.dp))
            NowPlayingMovies(homeUIState, getSelectedMovieDetails)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun UpcomingMovies(
    homeUIState: HomeUIState,
    getSelectedMovieDetails: (Int) -> Unit,
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
    ) {
        items(
            items = homeUIState.upcomingMoviesList,
            key = { it.movieId }
        ) { movieItem ->
            LoadNetworkImage(
                imageUrl = movieItem.posterPathUrl,
                contentDescription = "",
                shape = MaterialTheme.shapes.large,
                showAnimProgress = false,
                modifier = Modifier
                    .fillParentMaxSize()
                    .clickable {
                        getSelectedMovieDetails(movieItem.movieId)
                    }
            )
        }
    }
}

@Composable
private fun NowPlayingMovies(
    homeUIState: HomeUIState,
    getSelectedMovieDetails: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
    ) {
        items(
            items = homeUIState.nowPlayingMoviesList,
            key = { it.movieId }
        ) { movieItem ->
            LoadNetworkImage(
                imageUrl = movieItem.posterPathUrl,
                contentDescription = stringResource(R.string.cd_movie_poster),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .size(120.dp, 180.dp)
                    .clickable {
                        getSelectedMovieDetails(movieItem.movieId)
                    }
            )
        }
    }
}

@Preview
@Composable
private fun MoviesTabContentPreview() {
    ComposeActorsTheme {
        MoviesTabContent(
            homeUIState = HomeUIState(
                popularActorList = listOf(),
                trendingActorList = listOf(),
                isFetchingActors = false,
                upcomingMoviesList = listOf(),
                nowPlayingMoviesList = listOf()
            ),
            getSelectedMovieDetails = {},
            homeSheetUIState = HomeSheetUIState()
        )
    }
}