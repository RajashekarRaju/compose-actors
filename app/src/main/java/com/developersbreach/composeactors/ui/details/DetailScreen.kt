package com.developersbreach.composeactors.ui.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.developersbreach.composeactors.model.Actor
import com.developersbreach.composeactors.ui.actors.ActorsViewModel

@Composable
fun DetailScreen(
    actorId: Int,
    navigateUp: () -> Unit,
    viewModel: ActorsViewModel
) {

     val actor: Actor? = remember(actorId) {
         viewModel.getSelectedActor(
             actorId
         )
     }
}