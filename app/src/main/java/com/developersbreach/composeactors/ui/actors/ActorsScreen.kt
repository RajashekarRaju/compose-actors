package com.developersbreach.composeactors.ui.actors

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.model.Actor

@Composable
fun ActorsScreen(
    selectedActor: (Int) -> Unit,
    viewModel: ActorsViewModel
) {

    val viewState = viewModel.uiState.collectAsState()

    Surface(
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    { SearchBar() },
                    backgroundColor = MaterialTheme.colors.background,
                    elevation = 0.dp
                )
            }
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(viewState.value.actorsList) { actor ->
                    ActorRow(actor, selectedActor)
                }
            }
        }
    }
}

@Composable
fun ActorRow(
    actor: Actor,
    selectedActor: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = actor.actorName,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.clickable(onClick = { selectedActor(actor.actorId) })
        )
    }
}
