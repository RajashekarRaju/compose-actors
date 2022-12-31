package com.developersbreach.composeactors.ui.screens.actorDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.ui.screens.actorDetails.composables.ActorBiography
import com.developersbreach.composeactors.ui.screens.actorDetails.composables.ActorCastedMovies
import com.developersbreach.composeactors.ui.screens.actorDetails.composables.ActorInfoHeader
import com.developersbreach.composeactors.ui.screens.actorDetails.composables.ActorRoundProfile
import kotlinx.coroutines.Job

// Main content for this screen
@Composable
internal fun ActorDetailsContent(
    navigateUp: () -> Unit,
    detailUIState: ActorDetailsUIState,
    openActorDetailsBottomSheet: () -> Job,
    getSelectedMovieDetails: (Int) -> Unit
) {
    val actorData = detailUIState.actorData

    /** Sticky actor details content */
    Spacer(modifier = Modifier.padding(top = 16.dp))
    ActorRoundProfile("${actorData?.profileUrl}")
    Spacer(modifier = Modifier.padding(vertical = 8.dp))

    /** Scrollable actor details content */
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ActorInfoHeader(actorData)
        }
        item {
            ActorCastedMovies(
                detailUIState,
                openActorDetailsBottomSheet,
                getSelectedMovieDetails
            )
        }
        item {
            ActorBiography(actorData?.biography)
        }
    }
}