package com.developersbreach.composeactors.ui.screens.modalSheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.data.datasource.fake.fakeMovieDetail
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import com.developersbreach.composeactors.ui.components.CircularSeparator
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.screens.movieDetail.composables.MovieGenre
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.composeactors.utils.getMovieRuntimeFormatted
import com.developersbreach.designsystem.components.CaDivider
import com.developersbreach.designsystem.components.CaIconButton
import com.developersbreach.designsystem.components.CaText
import com.developersbreach.designsystem.components.CaTextH6
import com.developersbreach.designsystem.components.CaTextSubtitle1

/**
 * Content inside modal sheet.
 *
 * @param navigateToSelectedMovie navigates to selected movie details after clicking the icon button
 * in modal bottom sheet in cast detail screen.
 *
 * This modal sheet shows few details of selected movie from specific cast instead of directly
 * navigating to detail screen.
 */
@Composable
fun SheetContentMovieDetails(
    movie: MovieDetail?,
    navigateToSelectedMovie: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        HeaderModalSheet(movie, navigateToSelectedMovie)
        Spacer(modifier = Modifier.height(4.dp))
        SeparatorSheetTitleHeader()
        Spacer(modifier = Modifier.height(16.dp))
        MovieGenre(genres = movie?.genres)
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularSeparator()
            MovieReleaseDateText(movie?.releaseDate)
            CircularSeparator()
            MovieDurationText(movie?.runtime)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            MoviePosterImage(movie, navigateToSelectedMovie)
            MovieOverviewText(movie?.overview.toString())
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun HeaderModalSheet(
    movie: MovieDetail?,
    onClickMovie: (Int) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 20.dp)
    ) {
        CaTextH6(
            text = "${movie?.movieTitle}",
            color = MaterialTheme.colors.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(8f)
                .align(alignment = Alignment.CenterVertically)
        )

        // On clicking this icon triggers detail screen navigation with movie Id.
        CaIconButton(
            onClick = {
                if (movie != null) {
                    onClickMovie(movie.movieId)
                }
            },
            modifier = Modifier
                .weight(2f)
                .align(alignment = Alignment.CenterVertically),
            iconModifier = Modifier,
            painter = painterResource(id = R.drawable.ic_chevron_right),
            tint = MaterialTheme.colors.onSurface.copy(alpha = 0.75f),
            contentDescription = ""
        )
    }
}

@Composable
private fun SeparatorSheetTitleHeader() {
    CaDivider(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.50f),
        thickness = 0.50.dp
    )
}

@Composable
private fun MovieReleaseDateText(
    releaseDate: String?
) {
    CaTextSubtitle1(
        text = "${releaseDate?.take(4)}",
        color = MaterialTheme.colors.onSurface.copy(0.7f),
        maxLines = 1,
        modifier = Modifier
    )
}

@Composable
private fun MovieDurationText(
    runtime: Int?
) {
    CaTextSubtitle1(
        text = getMovieRuntimeFormatted(runtime),
        color = MaterialTheme.colors.onSurface.copy(0.7f),
        modifier = Modifier,
        maxLines = 1
    )
}

@Composable
private fun MoviePosterImage(
    movie: MovieDetail?,
    selectedMovie: (Int) -> Unit
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
}

@Composable
private fun MovieOverviewText(
    overview: String
) {
    CaText(
        text = overview,
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

@PreviewLightDark
@Composable
fun SheetContentMovieDetailsPreview() {
    ComposeActorsTheme {
        SheetContentMovieDetails(
            movie = fakeMovieDetail,
            navigateToSelectedMovie = {}
        )
    }
}