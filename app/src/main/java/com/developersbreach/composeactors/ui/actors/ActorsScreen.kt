package com.developersbreach.composeactors.ui.actors

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.developersbreach.composeactors.model.Actor
import com.google.accompanist.insets.statusBarsPadding


@Composable
fun ActorsScreen(
    selectedActor: (Int) -> Unit,
    navigateToSearch: () -> Unit,
    viewModel: ActorsViewModel
) {

    val viewState by viewModel.actorsViewState.observeAsState(ActorsViewState())

    Surface(
        color = MaterialTheme.colors.primarySurface
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    { SearchBar() },
                    backgroundColor = MaterialTheme.colors.background,
                    elevation = 0.dp,
                    modifier = Modifier
                        .clickable(onClick = navigateToSearch)
                        .statusBarsPadding()
                )
            }
        ) {
            ScreenContent(selectedActor, viewState)
        }
    }
}

@Composable
fun ScreenContent(
    selectedActor: (Int) -> Unit,
    viewState: ActorsViewState
) {
    Column {
        Text(
            text = "Popular",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(start = 24.dp, top = 16.dp)
        )
        Spacer(modifier = Modifier.padding(top = 16.dp))
        ActorRow(viewState.popularActorList, selectedActor)

        Spacer(modifier = Modifier.padding(top = 24.dp))

        Text(
            text = "Trending",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(start = 24.dp, top = 16.dp)
        )
        Spacer(modifier = Modifier.padding(top = 16.dp))
        ActorRow(viewState.trendingActorList, selectedActor)
    }
}

@Composable
private fun ActorRow(
    actors: List<Actor>,
    selectedActor: (Int) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(actors) { actor ->
            ItemActorList(actor = actor, selectedActor = selectedActor)
        }
    }
}

@Composable
fun ItemActorList(
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
            Spacer(modifier = Modifier.padding(top = 24.dp))
            Image(
                painter = rememberImagePainter(actor.profilePath),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))

            Text(
                text = actor.actorName,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.padding(top = 20.dp))
        }
    }
}