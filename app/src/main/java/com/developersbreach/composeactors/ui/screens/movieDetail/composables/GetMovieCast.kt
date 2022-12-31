package com.developersbreach.composeactors.ui.screens.movieDetail.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.model.Cast
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.screens.movieDetail.MovieDetailsUIState
import kotlinx.coroutines.Job

@Composable
 fun GetMovieCast(
    uiState: MovieDetailsUIState,
    openMovieDetailsBottomSheet: () -> Job,
    getSelectedActorDetails: (Int) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(
            items = uiState.movieCast,
            key = { it.actorId }
        ) { cast ->
            ItemCast(
                cast = cast,
                movieDetailsUIState = uiState,
                openMovieDetailsBottomSheet = openMovieDetailsBottomSheet,
                getSelectedActorDetails = getSelectedActorDetails
            )
        }
    }
}

@Composable
private fun ItemCast(
    cast: Cast,
    movieDetailsUIState: MovieDetailsUIState,
    openMovieDetailsBottomSheet: () -> Job,
    getSelectedActorDetails: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(120.dp)
            .clickable {
                getSelectedActorDetails(cast.actorId)
                openMovieDetailsBottomSheet()
            }
    ) {
        LoadNetworkImage(
            imageUrl = cast.castProfilePath,
            contentDescription = stringResource(R.string.cd_actor_image),
            shape = CircleShape,
            modifier = Modifier
                .size(120.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                    shape = CircleShape
                )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = cast.castName,
            style = MaterialTheme.typography.subtitle2,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        )
    }
}