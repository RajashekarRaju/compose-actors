package com.developersbreach.composeactors.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.data.model.Actor
import com.developersbreach.composeactors.ui.screens.actorDetails.ActorDetailsScreen
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme

/**
 * @param actorsList searchable results row list elements of [Actor]
 */
@Composable
fun ActorSearchUI(
    actorsList: List<Actor>,
    selectedActor: (Int) -> Unit,
    closeKeyboard: () -> Unit?
) {
    LazyColumn(
        // This padding helps avoid content going behind the navigation bars.
        modifier = Modifier.padding(bottom = 48.dp)
    ) {
        items(actorsList) { actor ->
            ItemSearchActor(actor, selectedActor, closeKeyboard)
        }
    }
}

/**
 * @param selectedActor navigate to actor [ActorDetailsScreen] from user selected actor.
 */
@Composable
private fun ItemSearchActor(
    actor: Actor,
    selectedActor: (Int) -> Unit,
    closeKeyboard: () -> Unit?
) {
    Text(
        text = actor.actorName,
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .wrapContentWidth(Alignment.Start)
            .clickable {
                closeKeyboard()
                selectedActor(actor.actorId)
            }
    )
}

@Preview
@Composable
private fun ActorSearchUIPreview() {
    ComposeActorsTheme {
        ActorSearchUI(
            actorsList = listOf(),
            selectedActor = {},
            closeKeyboard = {}
        )
    }
}