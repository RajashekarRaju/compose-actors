package com.developersbreach.composeactors.ui.screens.home.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.ui.components.CategoryTitle
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.components.itemsPaging
import com.developersbreach.composeactors.ui.screens.home.HomeSheetUIState
import com.developersbreach.composeactors.ui.screens.home.HomeUIState
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun MoviesTabContent(
    homeUIState: HomeUIState,
    navigateToSelectedMovie: (Int) -> Unit,
    homeSheetUIState: HomeSheetUIState,
) {
    val nowPlayingMovies = homeUIState.nowPlayingMoviesList.collectAsLazyPagingItems()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item(
            span = { GridItemSpan(3) }
        ) {
            CategoryTitle(
                title = "Upcoming",
                startPadding = 0.dp,
                bottomPadding = 8.dp
            )
        }

        item(
            span = { GridItemSpan(3) },
            content = {
                UpcomingMovies(
                    homeUIState = homeUIState,
                    navigateToSelectedMovie = navigateToSelectedMovie,
                    modifier = Modifier
                        .height(140.dp)
                        .fillMaxWidth()
                )
            }
        )

        item(
            span = { GridItemSpan(3) }
        ) {
            CategoryTitle(
                title = "Now Playing",
                startPadding = 0.dp,
                topPadding = 16.dp,
                bottomPadding = 8.dp
            )
        }

        nowPlayingMovies(
            listItems = nowPlayingMovies,
            navigateToSelectedMovie = navigateToSelectedMovie,
        )
    }
}

@Composable
private fun UpcomingMovies(
    homeUIState: HomeUIState,
    navigateToSelectedMovie: (Int) -> Unit,
    modifier: Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(
            items = homeUIState.upcomingMoviesList,
            key = { it.movieId }
        ) { movieItem ->
            LoadNetworkImage(
                imageUrl = movieItem.posterPathUrl,
                contentDescription = movieItem.movieName,
                shape = MaterialTheme.shapes.large,
                showAnimProgress = false,
                modifier = modifier
                    .width(260.dp)
                    .clickable {
                        navigateToSelectedMovie(movieItem.movieId)
                    }
            )
        }
    }
}

private fun LazyGridScope.nowPlayingMovies(
    listItems: LazyPagingItems<Movie>,
    navigateToSelectedMovie: (Int) -> Unit,
) {
    itemsPaging(listItems) { movie ->
        LoadNetworkImage(
            imageUrl = movie?.posterPathUrl,
            contentDescription = stringResource(R.string.cd_movie_poster),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .size(120.dp, 180.dp)
                .clickable {
                    if (movie != null) {
                        navigateToSelectedMovie(movie.movieId)
                    }
                }
        )
    }
}

@Preview
@Composable
private fun MoviesTabContentPreview() {
    ComposeActorsTheme {
        MoviesTabContent(
            homeUIState = HomeUIState(
                popularPersonList = listOf(),
                trendingPersonList = listOf(),
                isFetchingPersons = false,
                upcomingMoviesList = listOf(),
                nowPlayingMoviesList = emptyFlow()
            ),
            navigateToSelectedMovie = {},
            homeSheetUIState = HomeSheetUIState()
        )
    }
}