package com.developersbreach.composeactors.ui.actors

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.repository.ActorsRepository
import com.developersbreach.composeactors.ui.details.Actor

@Composable
fun Actors(
    selectedActor: (Int) -> Unit
) {

    val actors: List<Actor> = ActorsRepository().actorsData

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(actors) { actor ->
            ActorRow(actor, selectedActor)
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
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.clickable(onClick = { selectedActor(actor.actorId) })
        )
    }
}
