package com.developersbreach.composeactors.ui.screens.actorDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
    data: ActorDetailsData,
    openActorDetailsBottomSheet: () -> Job,
    getSelectedMovieDetails: (Int) -> Unit,
    showFab: MutableState<Boolean>,
) {
    val actorData = data.actorData
    val listState = rememberLazyListState()

    /** Sticky actor details content */
    Spacer(modifier = Modifier.padding(top = 16.dp))
    ActorRoundProfile("${actorData?.profileUrl}")
    Spacer(modifier = Modifier.padding(vertical = 8.dp))

    showFab.value = !listState.isScrollInProgress

    /** Scrollable actor details content */
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            ActorInfoHeader(actorData)
        }
        item {
            ActorCastedMovies(
                data,
                openActorDetailsBottomSheet,
                getSelectedMovieDetails,
            )
        }
        item {
            ActorBiography(actorData?.biography)
        }
    }
}