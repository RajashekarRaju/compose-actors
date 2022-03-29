package com.developersbreach.composeactors.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
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
import com.developersbreach.composeactors.model.Actor
import com.developersbreach.composeactors.ui.components.*
import com.developersbreach.composeactors.ui.details.DetailScreen
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme

/**
 * @param selectedActor navigates to user clicked actor from row.
 * @param navigateToSearch navigates user to search screen.
 * @param viewModel to manage ui state of [HomeScreen]
 *
 * Default destination.
 * Shows category list of actors in row.
 * Shows [HomeAppBar] search box looking ui in [TopAppBar]
 * If user is offline shows snackbar message.
 */
@Composable
fun HomeScreen(
    selectedActor: (Int) -> Unit,
    navigateToSearch: () -> Unit,
    viewModel: HomeViewModel
) {
    val uiState = viewModel.uiState
    // Remember state of scaffold to manage snackbar
    val scaffoldState = rememberScaffoldState()

    Surface(
        color = MaterialTheme.colors.background,
    ) {
        Scaffold(
            // attach snackbar host state to the scaffold
            scaffoldState = scaffoldState,
            // Custom AppBar contains fake search bar.
            topBar = { MainAppBar(navigateToSearch) },
            // Host for custom snackbar
            snackbarHost = { hostState ->
                SnackbarHost(hostState) { data ->
                    Snackbar(
                        snackbarData = data,
                        // To avoid colliding with navigation bar.
                        modifier = Modifier.padding(bottom = 48.dp)
                    )
                }
            }
        ) {
            Box {
                // Show progress while data is loading
                ShowProgressIndicator(isLoadingData = uiState.isFetchingActors)

                // Main content for this screen
                ScreenContent(selectedActor, uiState)

                // Perform network check and show snackbar if offline
                IfOfflineShowSnackbar(scaffoldState)

                // If Api key is added, show a SnackBar.
                ApiKeyMissingShowSnackbar(scaffoldState)
            }
        }
    }
}

// Main content for this screen.
@Composable
private fun ScreenContent(
    selectedActor: (Int) -> Unit,
    viewState: HomeViewState
) {
    LazyColumn {
        item { Spacer(modifier = Modifier.padding(vertical = 16.dp)) }
        // Show text for home category list popular.
        item { CategoryTitle(stringResource(R.string.category_actors_popular)) }
        item { Spacer(modifier = Modifier.padding(vertical = 8.dp)) }
        // List row of all popular actors.
        item { ItemActorList(viewState.popularActorList, selectedActor) }
        item { AppDivider(verticalPadding = 32.dp) }
        // Show text for actors category list trending.
        item { CategoryTitle(stringResource(R.string.category_actors_trending)) }
        item { Spacer(modifier = Modifier.padding(vertical = 8.dp)) }
        // List row of all trending actors.
        item { ItemActorList(viewState.trendingActorList, selectedActor) }
        // Extra spacing to avoid content collide with navigation bars.
        item { Spacer(modifier = Modifier.padding(vertical = 32.dp)) }
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
        modifier = Modifier.padding(start = 16.dp)
    ) {
        items(actorsList) { actor ->
            ItemActor(actor = actor, selectedActor = selectedActor)
        }
    }
}

/**
 * @param selectedActor navigate to actor [DetailScreen] from user selected actor.
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

/**
 * Appbar contains [HomeAppBar] which does not perform search query directly.
 * Instead navigates to search screen to submit query.
 * @param navigateToSearch navigates user to search screen.
 */
@Composable
private fun MainAppBar(
    navigateToSearch: () -> Unit
) {
    TopAppBar(
        content = { HomeAppBar(navigateToSearch) },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        modifier = Modifier.padding(top = 4.dp, start = 16.dp, end = 16.dp),
        contentPadding = WindowInsets.statusBars
            .only(WindowInsetsSides.Top)
            .asPaddingValues()
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    ComposeActorsTheme(darkTheme = true) {
        ScreenContent({ }, HomeViewState())
    }
}