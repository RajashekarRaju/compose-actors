package com.developersbreach.composeactors.ui.screens.search

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalView

/**
 * This will ensure that first to close the keyboard before the user navigates away to previous or
 * details screen.
 *
 * @param navigateUp navigates up only when the keyboard is closed, or closes keyboard then navigate.
 * @param closeKeyboard check whether on current screen a keyboard is opened.
 * @param keyboardState determines current keyboard state.
 */
fun closeKeyboardAndNavigateUp(
    navigateUp: () -> Unit,
    closeKeyboard: () -> Unit?,
    keyboardState: KeyboardState,
) {
    if (keyboardState == KeyboardState.Opened) {
        closeKeyboard()
    } else if (keyboardState == KeyboardState.Closed) {
        navigateUp()
    }
}

enum class KeyboardState {
    Opened, Closed
}

@Composable
fun getCurrentKeyboardState(): State<KeyboardState> {

    val keyboardState = remember { mutableStateOf(KeyboardState.Closed) }
    val view = LocalView.current

    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            when {
                keypadHeight > screenHeight * 0.15 -> KeyboardState.Opened
                else -> KeyboardState.Closed
            }.also {
                keyboardState.value = it
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }
    return keyboardState
}

/**
 * Launched keyboard once search screen opens.
 * Not useful in our scenario.
 *
 * Used to ensure a TextField is focused when showing keyboard
 * val focusRequester = remember { FocusRequester() }
 *
 * Let user search immediately by calling this composable
 * LaunchKeyboardOnScreenOpen(focusRequester, keyboardController)
 *
 * Then call focus requester to the modifier.
 * modifier = Modifier.focusRequester(focusRequester)
 */
/*
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LaunchKeyboardOnScreenOpen(
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
) {
    LaunchedEffect(true) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }
}
*/