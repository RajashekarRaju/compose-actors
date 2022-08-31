package com.developersbreach.composeactors.ui.screens.actorDetails.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.ui.screens.actorDetails.data.model.DetailsUIState
import kotlinx.coroutines.Job

// Main content for this screen
@Composable
fun ActorDetailsContent(
    navigateUp: () -> Unit,
    detailUIState: DetailsUIState,
    openActorDetailsBottomSheet: () -> Job,
    getSelectedMovieDetails: (Int) -> Unit
) {
    val actorData = detailUIState.actorData

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
            item { ActorCastedMovies(detailUIState, openActorDetailsBottomSheet, getSelectedMovieDetails) }
            item { ActorBiography(actorData?.biography) }
        }
    }
}