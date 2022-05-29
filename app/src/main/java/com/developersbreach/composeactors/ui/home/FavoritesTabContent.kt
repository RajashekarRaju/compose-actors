package com.developersbreach.composeactors.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 * Content shown for home tab Favorites.
 * If user did not add any movies to favorites, a message will be shown.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoritesTabContent(
    viewModel: HomeViewModel,
    selectedMovie: (Int) -> Unit,
    modalSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val favoritesList by viewModel.favoriteMovies.observeAsState(emptyList())
    if (favoritesList.isEmpty()) {
        ShowNoFavoritesFound()
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 2.dp, horizontal = 16.dp)
    ) {
        items(
            items = favoritesList,
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
                        coroutineScope.launch {
                            modalSheetState.show()
                        }
                    }
            )
        }
    }
}

@Composable
private fun ShowNoFavoritesFound() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_no_favorites),
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Text(
                text = stringResource(R.string.no_favorites_found_message),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 40.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}