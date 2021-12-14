package com.developersbreach.composeactors.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.developersbreach.composeactors.model.ActorDetail
import com.developersbreach.composeactors.model.Movie
import com.developersbreach.composeactors.utils.ActorDynamicTheme
import com.developersbreach.composeactors.utils.verticalGradientScrim


@Composable
fun DetailScreen(
    navigateUp: () -> Unit,
    viewModel: DetailsViewModel
) {
    val actor by viewModel.uiState.observeAsState()
    val cast by viewModel.castState.observeAsState(listOf())
    val actorProfile = "${actor?.profilePath}"

    Surface(
        color = MaterialTheme.colors.background
    ) {
        ActorDynamicTheme(
            podcastImageUrl = actorProfile
        ) {
            Box {
                ActorBackgroundWithGradientForeground(
                    podcastImageUrl = actorProfile
                )
                Column {
                    ContentDetail(navigateUp, actor, cast)
                }
            }
        }
    }
}

@Composable
private fun ContentDetail(
    navigateUp: () -> Unit,
    actor: ActorDetail?,
    cast: List<Movie>
) {
    DetailAppBar(
        navigateUp = navigateUp,
        title = "${actor?.actorName}"
    )

    Spacer(modifier = Modifier.padding(top = 24.dp))

    ActorRoundProfile("${actor?.profilePath}")

    Spacer(modifier = Modifier.padding(top = 24.dp))

    ActorCastedMovies(cast)
}

@Composable
fun ActorRoundProfile(
    profileUrl: String
) {
    Image(
        painter = rememberImagePainter(data = profileUrl),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(120.dp).clip(shape = CircleShape)
    )
}

@Composable
private fun ActorCastedMovies(cast: List<Movie>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(cast) { movie ->
            Image(
                painter = rememberImagePainter(data = movie.posterPath),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp, 150.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
            )
        }
    }
}


@Composable
fun ActorBackgroundWithGradientForeground(
    podcastImageUrl: String,
    modifier: Modifier = Modifier
) {
    Box {
        Image(
            painter = rememberImagePainter(data = podcastImageUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxSize()
                .alpha(0.1f)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalGradientScrim(
                    color = MaterialTheme.colors.primary.copy(alpha = 0.50f),
                    startYPercentage = 1f,
                    endYPercentage = 0f
                )
        )
    }
}

@Composable
fun DetailAppBar(
    navigateUp: () -> Unit,
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = navigateUp) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                tint = MaterialTheme.colors.onBackground,
                contentDescription = null
            )
        }

        Text(
            text = title,
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}