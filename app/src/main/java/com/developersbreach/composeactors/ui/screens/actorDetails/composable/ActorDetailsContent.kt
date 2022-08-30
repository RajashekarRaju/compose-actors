package com.developersbreach.composeactors.ui.screens.actorDetails.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.screens.actorDetails.ActorDetailsViewModel
import kotlinx.coroutines.Job

// Main content for this screen
@Composable
fun ActorDetailsContent(
    navigateUp: () -> Unit,
    viewModel: ActorDetailsViewModel,
    openActorDetailsBottomSheet: () -> Job
) {
    val actorData = viewModel.uiState.actorData

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
            item { ActorCastedMovies(viewModel, openActorDetailsBottomSheet) }
            item { ActorBiography(actorData?.biography) }
        }
    }
}

/**
 * Load circular network image of actor at the top of screen.
 */
@Composable
private fun ActorRoundProfile(
    profileUrl: String,
    size: Dp = 120.dp
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LoadNetworkImage(
            imageUrl = profileUrl,
            contentDescription = stringResource(id = R.string.cd_actor_image),
            shape = CircleShape,
            modifier = Modifier
                .size(size)
                .border(
                    width = 4.dp,
                    color = MaterialTheme.colors.surface,
                    shape = CircleShape
                )
        )
    }
}