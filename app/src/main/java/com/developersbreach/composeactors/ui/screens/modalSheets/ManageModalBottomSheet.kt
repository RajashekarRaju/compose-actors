package com.developersbreach.composeactors.ui.screens.modalSheets

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * This modal sheet is used across all screens with default sheet config.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun modalBottomSheetState(
    initialValue: ModalBottomSheetValue = ModalBottomSheetValue.Hidden,
    skipHalfExpanded: Boolean = true,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
): ModalBottomSheetState {
    return rememberModalBottomSheetState(
        initialValue = initialValue,
        skipHalfExpanded = skipHalfExpanded,
        animationSpec = animationSpec,
    )
}

/**
 * We no more have to create coroutine scope or launch effects anywhere in any composables when we
 * have to use [ModalBottomSheetState] to manage it show or hide states.
 * Not necessary to check whether sheet is open or not, if the current sheet state is Visible sheet
 * will close, if sheet state is Hidden then it will open.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun manageModalBottomSheet(
    modalSheetState: ModalBottomSheetState
): () -> Job {
    val coroutineScope = rememberCoroutineScope()

    val hideOrShowModalBottomSheet = {
        coroutineScope.launch {
            when {
                modalSheetState.isVisible -> modalSheetState.hide()
                !modalSheetState.isVisible -> modalSheetState.show()
            }
        }
    }

    return hideOrShowModalBottomSheet
}