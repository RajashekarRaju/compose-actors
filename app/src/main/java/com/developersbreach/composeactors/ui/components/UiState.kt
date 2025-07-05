package com.developersbreach.composeactors.ui.components

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()

    data class Success<out T>(
        val data: T,
    ) : UiState<T>()

    data class Error(
        val throwable: Throwable,
    ) : UiState<Nothing>()
}

fun <T> UiState<T>.modifyLoadedState(
    transform: T.() -> T,
): UiState<T> = when (this) {
    is UiState.Success -> UiState.Success(transform(this.data))
    else -> this
}

fun <T> UiState<T>.getLoadedState(): T {
    return (this as UiState.Success).data
}