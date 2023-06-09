package com.developersbreach.composeactors.ui.screens.modalSheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.model.Flatrate
import com.developersbreach.composeactors.data.model.MovieProvider
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.screens.movieDetail.composables.FloatingAddToFavoritesButton

@Composable
fun SheetContentMovieProviders(
    movieProvider: MovieProvider?,
    isFavoriteMovie: Boolean,
    addMovieToFavorites: () -> Unit,
    removeMovieFromFavorites: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .navigationBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        HeaderModalSheet()
        Spacer(modifier = Modifier.height(8.dp))
        if (movieProvider != null && movieProvider.flatrate.isNotEmpty()) {
            Streaming(movieProvider.flatrate)
        } else {
            NoStreaming()
        }
        FloatingAddToFavoritesButton(
            isFavorite = isFavoriteMovie,
            addToFavorites = addMovieToFavorites,
            removeFromFavorites = removeMovieFromFavorites
        )
    }
}

@Composable
fun NoStreaming() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.no_watch_options),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
fun Streaming(flatrate: ArrayList<Flatrate>) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 24.dp)
    ) {
        LazyRow {
            items(flatrate) { flatrate ->
                LoadNetworkImage(
                    imageUrl = flatrate.logo_path,
                    contentDescription = "",
                    shape = MaterialTheme.shapes.large,
                    showAnimProgress = false,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(48.dp)
                )
            }
        }
    }
}

@Composable
private fun HeaderModalSheet() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_drag_24),
            contentDescription = "",
            modifier = Modifier.size(24.dp)
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = stringResource(id = R.string.stream), style = MaterialTheme.typography.h6)
    }
}