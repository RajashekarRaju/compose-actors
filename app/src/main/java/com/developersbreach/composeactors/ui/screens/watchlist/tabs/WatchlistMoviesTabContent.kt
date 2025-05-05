package com.developersbreach.composeactors.ui.screens.watchlist.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.data.datasource.fake.fakeMovieList
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.screens.watchlist.NoWatchlistFoundUI
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaIconButton
import com.developersbreach.designsystem.components.CaTextSubtitle1

@Composable
fun WatchlistMoviesTabContent(
    navigateToSelectedMovie: (Int) -> Unit,
    watchlistMovies: List<Movie>,
    removeMovieFromWatchlist: (Movie) -> Unit,
) {
    if (watchlistMovies.isEmpty()) {
        NoWatchlistFoundUI()
    }

    val listState = rememberLazyListState()

    LazyColumn(
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        state = listState,
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(watchlistMovies) { movieItem: Movie ->
            ItemWatchlistMovie(
                movieItem = movieItem,
                onClickMovie = navigateToSelectedMovie,
                removeMovieFromWatchlist = removeMovieFromWatchlist,
            )
        }
    }
}

@Composable
private fun ItemWatchlistMovie(
    movieItem: Movie,
    onClickMovie: (Int) -> Unit,
    removeMovieFromWatchlist: (Movie) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .background(MaterialTheme.colors.surface),
    ) {
        LoadNetworkImage(
            imageUrl = movieItem.bannerUrl,
            contentDescription = stringResource(R.string.cd_movie_poster),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable { onClickMovie(movieItem.movieId) },
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            CaTextSubtitle1(
                text = movieItem.movieName,
                color = MaterialTheme.colors.primary,
                lineHeight = 20.sp,
                modifier = Modifier.weight(0.8f),
            )
            CaIconButton(
                iconModifier = Modifier,
                onClick = { removeMovieFromWatchlist(movieItem) },
                modifier = Modifier.weight(0.1f),
                painter = painterResource(id = R.drawable.ic_added_to_watchlist),
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun WatchlistMoviesTabContentPreview() {
    ComposeActorsTheme {
        WatchlistMoviesTabContent(
            navigateToSelectedMovie = {},
            watchlistMovies = fakeMovieList(),
            removeMovieFromWatchlist = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun WatchlistMoviesTabContentNoWatchlistPreview() {
    ComposeActorsTheme {
        WatchlistMoviesTabContent(
            navigateToSelectedMovie = {},
            watchlistMovies = emptyList(),
            removeMovieFromWatchlist = {},
        )
    }
}