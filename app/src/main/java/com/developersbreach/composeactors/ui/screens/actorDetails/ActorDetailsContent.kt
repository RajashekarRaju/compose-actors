package com.developersbreach.composeactors.ui.screens.actorDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.model.ActorDetail
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.ui.animations.borderRevealAnimation
import com.developersbreach.composeactors.ui.components.CategoryTitle
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.components.verticalGradientScrim
import com.developersbreach.composeactors.utils.calculateAge
import com.developersbreach.composeactors.utils.getPlaceOfBirth
import com.developersbreach.composeactors.utils.getPopularity
import kotlinx.coroutines.Job

// Main content for this screen
@Composable
fun ActorDetailsContent(
    navigateUp: () -> Unit,
    viewModel: ActorDetailsViewModel,
    openActorDetailsBottomSheet: () -> Job
) {
    val actorData = viewModel.uiState.actorData

    Column {

        ActorDetailsTopAppBar(
            navigateUp = navigateUp,
            title = "${actorData?.actorName}"
        )

        /** Sticky actor details content */
        Spacer(modifier = Modifier.padding(top = 16.dp))

        ActorRoundProfile("${actorData?.profileUrl}")

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        /** Scrollable actor details content */
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { ActorInfoHeader(actorData) }
            item { ActorCastedMovies(viewModel, openActorDetailsBottomSheet) }
            item { ActorBiography(actorData?.biography) }
        }
    }
}

/**
 * Load circular network image of actor at the top of screen.
 */
@Composable
private fun ActorRoundProfile(
    profileUrl: String,
    size: Dp = 120.dp
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LoadNetworkImage(
            imageUrl = profileUrl,
            contentDescription = stringResource(id = R.string.cd_actor_image),
            shape = CircleShape,
            modifier = Modifier
                .size(size)
                .border(
                    width = 4.dp,
                    color = MaterialTheme.colors.surface,
                    shape = CircleShape
                )
        )
    }
}

/**
 * Row with 3 elements which shows actor details just below actor image.
 * 1. Age of actor
 * 2. Popularity
 * 3. Place of birth
 */
@Composable
fun ActorInfoHeader(
    actorData: ActorDetail?
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        item { AgeInfo(actorAge = actorData?.dateOfBirth) }
        item { PopularityInfo(popularity = actorData?.popularity) }
        item { CountryInfo(placeOfBirth = actorData?.placeOfBirth) }
    }
}

/**
 * Cast icon and title on top and list below.
 */
@Composable
private fun ActorCastedMovies(
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

/**
 * @param biography shows paragraph with gradient background drawn from image.
 * Biography icon with title on top and paragraph below.
 */
@Composable
private fun ActorBiography(
    biography: String?
) {
    Column(
        modifier = Modifier
            .verticalGradientScrim(
                color = MaterialTheme.colors.surface.copy(alpha = 0.75f),
                startYPercentage = 0f,
                endYPercentage = 1f
            )
            .padding(
                bottom = 56.dp,
                start = 16.dp,
                end = 16.dp
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_biography),
                contentDescription = stringResource(id = R.string.cd_biography_icon),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface),
                alpha = 0.5f,
                modifier = Modifier.size(36.dp),
            )

            CategoryTitle(
                title = stringResource(R.string.cast_biography_title)
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        // Make use of style to modify line height for the paragraph.
        if (biography != null) {
            Text(
                text = biography,
                style = TextStyle(
                    lineHeight = 20.sp,
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Justify
                )
            )
        }
    }
}

/**
 * First element in [ActorInfoHeader].
 * Shows calculated age of actor from [calculateAge]
 */
@Composable
private fun AgeInfo(
    actorAge: String?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Box(
            modifier = Modifier.borderRevealAnimation()
        ) {
            Text(
                text = "${calculateAge(actorAge)}",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }

        ActorInfoHeaderSubtitle(
            subtitle = stringResource(R.string.actor_age_subtitle)
        )
    }
}

/**
 * Second element in [ActorInfoHeader].
 * Fetches place of birth from function [calculateAge].
 */
@Composable
private fun CountryInfo(
    placeOfBirth: String?
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.borderRevealAnimation()
        ) {
            Image(
                painterResource(id = R.drawable.ic_globe),
                contentDescription = stringResource(R.string.cd_place_of_birth_icon),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface),
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .size(32.dp)
            )
        }

        ActorInfoHeaderSubtitle(
            subtitle = "${getPlaceOfBirth(placeOfBirth)}"
        )
    }
}

/**
 * Third element in [ActorInfoHeader].
 * Fetches actors popularity from function [calculateAge].
 */
@Composable
private fun PopularityInfo(
    popularity: Double?
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.borderRevealAnimation()
        ) {
            Text(
                text = getPopularity(popularity),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(alignment = Alignment.Center)
            )
        }

        ActorInfoHeaderSubtitle(
            subtitle = stringResource(R.string.actor_popularity_subtitle)
        )
    }
}

@Composable
private fun ActorInfoHeaderSubtitle(
    subtitle: String
) {
    Text(
        text = subtitle,
        style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(vertical = 8.dp),
        color = MaterialTheme.colors.onBackground
    )
}