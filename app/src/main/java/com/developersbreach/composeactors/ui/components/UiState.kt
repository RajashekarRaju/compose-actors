package com.developersbreach.composeactors.ui.components

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()

    data class Success<out T>(
        val data: T,
    ) : UiState<T>()

    data class Error(
        val throwable: Throwable,
    ) : UiState<Nothing>()

    data class AppErrorState(
        val error: AppError,
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

sealed class UiEvent {
    data class ShowMessage(val message: String) : UiEvent()
}

/**
 * Helper extension functions for creating UiState instances
 */
fun <T> T.asSuccessState(): UiState<T> = UiState.Success(this)

fun Throwable.asErrorState(): UiState<Nothing> = UiState.Error(this)

fun AppError.asAppErrorState(): UiState<Nothing> = UiState.AppErrorState(this)

/**
 * Extension function to convert Throwable to AppErrorState using context
 */
fun Throwable.toAppErrorState(context: android.content.Context): UiState<Nothing> = 
    UiState.AppErrorState(this.toAppError(context))
