package com.developersbreach.composeactors.ui.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.developersbreach.composeactors.repository.ActorsRepository
import timber.log.Timber

data class Actor(
    val actorId: Int,
    val actorName: String
)

@Composable
fun ActorDetails(
    actorId: Int,
    navigateUp: () -> Unit
) {

    val actor: Actor = remember(actorId) {
        ActorsRepository().getActor(
            actorId
        )
    }

    Timber.e("Selected Actor id is ${actor.actorId} and name ${actor.actorName}")
}