package com.developersbreach.composeactors.ui.screens.home.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.components.CategoryTitle
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.screens.home.HomeViewModel
import kotlinx.coroutines.Job


@Composable
fun MoviesTabContent(
    viewModel: HomeViewModel,
    selectedMovie: (Int) -> Unit,
    openHomeBottomSheet: () -> Job
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            CategoryTitle(title = "Upcoming", alpha = 0.5f)
            Spacer(modifier = Modifier.height(16.dp))
            UpcomingMovies(viewModel, openHomeBottomSheet)
            Spacer(modifier = Modifier.height(28.dp))
            CategoryTitle(title = "Now Playing", alpha = 0.5f)
            Spacer(modifier = Modifier.height(8.dp))
            NowPlayingMovies(viewModel, selectedMovie, openHomeBottomSheet)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun UpcomingMovies(
    viewModel: HomeViewModel,
    openHomeBottomSheet: () -> Job
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
    ) {
        items(
            items = viewModel.uiState.upcomingMoviesList,
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
                        viewModel.getSelectedMovieDetails(movieItem.movieId)
                        openHomeBottomSheet()
                    }
            )
        }
    }
}

@Composable
private fun NowPlayingMovies(
    viewModel: HomeViewModel,
    selectedMovie: (Int) -> Unit,
    closeHomeBottomSheet: () -> Job
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
            items = viewModel.uiState.nowPlayingMoviesList,
            key = { it.movieId }
        ) { movieItem ->
            LoadNetworkImage(
                imageUrl = movieItem.posterPathUrl,
                contentDescription = stringResource(R.string.cd_movie_poster),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .size(120.dp, 180.dp)
                    .clickable {
                        viewModel.getSelectedMovieDetails(movieItem.movieId)
                        closeHomeBottomSheet()
                    }
            )
        }
    }
}