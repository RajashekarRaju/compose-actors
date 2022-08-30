package com.developersbreach.composeactors.ui.screens.actorDetails.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.ui.components.CategoryTitle
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.screens.actorDetails.ActorDetailsViewModel
import kotlinx.coroutines.Job

/**
 * Cast icon and title on top and list below.
 */
@Composable
internal fun ActorCastedMovies(
    viewModel: ActorDetailsViewModel,
    openActorDetailsBottomSheet: () -> Job
) {
    val cast: List<Movie> = viewModel.uiState.castList

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_movies_cast),
            contentDescription = stringResource(R.string.cd_cast_icon),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface),
            alpha = 0.5f,
            modifier = Modifier
                .padding(start = 12.dp)
                .size(36.dp)
        )

        CategoryTitle(
            title = stringResource(R.string.cast_movie_title)
        )
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            items = cast,
            key = { it.movieId }
        ) { movie ->
            LoadNetworkImage(
                imageUrl = movie.posterPathUrl,
                contentDescription = stringResource(R.string.cd_movie_poster),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .size(100.dp, 150.dp)
                    .clickable {
                        viewModel.getSelectedMovieDetails(movie.movieId)
                        openActorDetailsBottomSheet()
                    }
            )
        }
    }
}