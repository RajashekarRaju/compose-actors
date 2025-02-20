package com.developersbreach.composeactors.ui.screens.modalSheets

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun modalBottomSheetState(
    initialValue: ModalBottomSheetValue = ModalBottomSheetValue.Hidden,
    skipHalfExpanded: Boolean = true,
    animationSpec: AnimationSpec<Float> = ModalBottomSheetDefaults.AnimationSpec,
): ModalBottomSheetState {
    return rememberModalBottomSheetState(
        initialValue = initialValue,
        skipHalfExpanded = skipHalfExpanded,
        animationSpec = animationSpec,
    )
}

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