package com.developersbreach.composeactors.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.components.LoadNetworkImage

/**
 * Content inside modal sheet.
 *
 * @param selectedMovie navigates to selected movie details after clicking the icon button
 * in modal bottom sheet in cast detail screen.
 *
 * This modal sheet shows few details of selected movie from specific cast instead of directly
 * navigating to detail screen.
 */
@Composable
fun SheetContent(
    viewModel: DetailsViewModel,
    selectedMovie: (Int) -> Unit
) {
    val movie = viewModel.sheetUiState.selectedMovieDetails

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            Text(
                text = "${movie?.movieTitle}",
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(8f)
                    .align(alignment = Alignment.CenterVertically)
            )

            // On clicking this icon triggers detail screen navigation with movie Id.
            IconButton(
                onClick = {
                    if (movie != null) {
                        selectedMovie(movie.movieId)
                    }
                },
                modifier = Modifier
                    .weight(2f)
                    .align(alignment = Alignment.CenterVertically)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_right),
                    tint = MaterialTheme.colors.onSurface.copy(alpha = 0.75f),
                    contentDescription = ""
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.50f),
            thickness = 0.50.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            Modifier.padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                if (movie != null) {
                    items(movie.genres) { genre ->
                        Text(
                            text = genre,
                            color = MaterialTheme.colors.onSurface.copy(0.7f),
                            style = MaterialTheme.typography.subtitle1,
                            maxLines = 1,
                        )
                    }
                }
            }

            CircularSeparator()

            Text(
                text = "${movie?.releaseDate?.take(4)}",
                color = MaterialTheme.colors.onSurface.copy(0.7f),
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
            )

            CircularSeparator()

            Text(
                text = getRuntimeFormatted(movie?.runtime),
                color = MaterialTheme.colors.onSurface.copy(0.7f),
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            LoadNetworkImage(
                imageUrl = "${movie?.poster}",
                contentDescription = "",
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .size(100.dp, 150.dp)
                    .clickable {
                        if (movie != null) {
                            selectedMovie(movie.movieId)
                        }
                    }
            )

            Text(
                text = "${movie?.overview}",
                maxLines = 7,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                style = TextStyle(
                    lineHeight = 20.sp,
                    color = MaterialTheme.colors.onSurface.copy(0.8f),
                    textAlign = TextAlign.Justify,
                    fontSize = 16.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Spacer(
            Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        )
    }
}

@Composable
private fun getRuntimeFormatted(
    runtime: Int?
): String {
    val hours: Int? = runtime?.div(60)
    val minutes: Int? = runtime?.rem(60)
    return "${hours}h:${minutes}m"
}

@Composable
private fun CircularSeparator(
    horPadding: Dp = 12.dp,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = horPadding)
            .size(6.dp)
            .clip(CircleShape)
            .background(
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.25f),
                shape = CircleShape
            )
    )
}