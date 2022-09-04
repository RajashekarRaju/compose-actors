package com.developersbreach.composeactors.ui.screens.home.tabs

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.model.Actor
import com.developersbreach.composeactors.ui.components.AppDivider
import com.developersbreach.composeactors.ui.components.CategoryTitle
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.components.ShowProgressIndicator
import com.developersbreach.composeactors.ui.screens.actorDetails.ActorDetailsScreen
import com.developersbreach.composeactors.ui.screens.home.HomeUIState
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme


@Composable
fun ActorsTabContent(
    homeUIState: HomeUIState,
    getSelectedActorDetails: (Int) -> Unit
) {
    // Show progress while data is loading
    ShowProgressIndicator(isLoadingData = homeUIState.isFetchingActors)

    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        // Show text for home category list popular.
        item {
            CategoryTitle(stringResource(R.string.category_actors_popular))
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            // List row of all popular actors.
            ItemActorList(homeUIState.popularActorList, getSelectedActorDetails)
            AppDivider(verticalPadding = 32.dp)
            // Show text for actors category list trending.
            CategoryTitle(stringResource(R.string.category_actors_trending))
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            // List row of all trending actors.
            ItemActorList(homeUIState.trendingActorList, getSelectedActorDetails)
        }
    }
}

/**
 * @param actorsList row list elements of [Actor]
 */
@Composable
private fun ItemActorList(
    actorsList: List<Actor>,
    selectedActor: (Int) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(
            items = actorsList,
            key = { it.actorId }
        ) { actor ->
            ItemActor(
                actor = actor,
                selectedActor = selectedActor
            )
        }
    }
}

/**
 * @param selectedActor navigate to actor [ActorDetailsScreen] from user selected actor.
 */
@Composable
private fun ItemActor(
    actor: Actor,
    selectedActor: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clip(shape = MaterialTheme.shapes.large)
            .clickable(onClick = { selectedActor(actor.actorId) })
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(vertical = 12.dp))

            LoadNetworkImage(
                imageUrl = actor.profileUrl,
                contentDescription = stringResource(R.string.cd_actor_image),
                shape = CircleShape,
                modifier = Modifier
                    .size(120.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onSurface,
                        shape = CircleShape
                    )
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = actor.actorName,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.padding(vertical = 12.dp))
        }
    }
}

@Preview
@Composable
private fun ActorsTabContentPreview() {
    ComposeActorsTheme {
        ActorsTabContent(
            homeUIState = HomeUIState(
                popularActorList = listOf(),
                trendingActorList = listOf(),
                isFetchingActors = false,
                upcomingMoviesList = listOf(),
                nowPlayingMoviesList = listOf()
            ),
            getSelectedActorDetails = {}
        )
    }
}