package com.developersbreach.composeactors.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.model.Actor
import com.developersbreach.composeactors.ui.components.SearchBar
import com.developersbreach.composeactors.ui.actors.ActorsScreen
import com.developersbreach.composeactors.ui.components.AppDivider
import com.developersbreach.composeactors.ui.components.ShowSearchProgress
import com.developersbreach.composeactors.ui.details.DetailScreen


/**
 * @param selectedActor navigates to user clicked actor from row.
 * @param viewModel to manage ui state of [SearchScreen]
 * @param navigateUp navigates user to previous screen.
 *
 * This destination can be accessed only from [ActorsScreen].
 * Shows searchable category list of actors in row.
 * Shows [SearchBar] search box looking ui in [TopAppBar]
 */
@Composable
fun SearchScreen(
    selectedActor: (Int) -> Unit,
    viewModel: SearchViewModel,
    navigateUp: () -> Unit
) {
    val uiState = viewModel.uiState

    Surface(
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = {
                SearchAppBar(navigateUp, viewModel)
            }
        ) {
            Box {
                // Show progress while search is happening
                val isLoadingData = !uiState.isSearchingResults && uiState.actorList.isEmpty()
                ShowSearchProgress(isLoadingData)
                // Main content for this screen
                ItemActorList(uiState.actorList, selectedActor)
            }
        }
    }
}

/**
 * @param actorsList searchable results row list elements of [Actor]
 */
@Composable
private fun ItemActorList(
    actorsList: List<Actor>,
    selectedActor: (Int) -> Unit
) {
    LazyColumn {
        items(actorsList) { actor ->
            ItemActor(actor, selectedActor)
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
    Text(
        text = actor.actorName,
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                selectedActor(actor.actorId)
            })
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .wrapContentWidth(Alignment.Start)
    )
}

/**
 * @param navigateUp Navigates to previous screen.
 *
 * onValueChange = { onQueryChanged ->
 *     query = onQueryChanged
 *     if (onQueryChanged.isNotEmpty()) {
 *     viewModel.performQuery(onQueryChanged)
 * }
 *
 * If user makes changes to text, immediately updated it.
 * To avoid crash, only query when string isn't empty.
 * Pass latest query to refresh search results.
 */
@Composable
private fun SearchAppBar(
    navigateUp: () -> Unit,
    viewModel: SearchViewModel
) {
    // Immediately update and keep track of query from text field changes.
    var query: String by rememberSaveable { mutableStateOf("") }

    // This top padding avoids colliding content with app bar.
    Column(
        modifier = Modifier.padding(top = 24.dp)
    ) {
        TextField(
            value = query,
            onValueChange = { onQueryChanged ->
                // If user makes changes to text, immediately updated it.
                query = onQueryChanged
                // To avoid crash, only query when string isn't empty.
                if (onQueryChanged.isNotEmpty()) {
                    // Pass latest query to refresh search results.
                    viewModel.performQuery(onQueryChanged)
                }
            },
            leadingIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        tint = MaterialTheme.colors.onBackground,
                        contentDescription = stringResource(id = R.string.cd_search_icon)
                    )
                }
            },
            placeholder = { Text(text = stringResource(R.string.hint_search_query)) },
            textStyle = MaterialTheme.typography.subtitle1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
            ),
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background, RectangleShape),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
            )
        )

        // Divides content and search bar with line.
        AppDivider(verticalPadding = 0.dp)
    }
}