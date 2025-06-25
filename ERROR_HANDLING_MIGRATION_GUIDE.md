# Error Handling Migration Guide

This document outlines the new error handling system implemented in the Compose Actors app and provides guidance on how to migrate from the old system to the new one.

## Overview

The error handling system has been refactored to follow Compose best practices:

- **Inline messages** for validation errors (LOW severity)
- **Snackbars** for transient or recoverable issues (MEDIUM severity)  
- **Dialogs only for critical/blocking issues** (HIGH severity)

## New Components

### 1. AppError Sealed Class Hierarchy

```kotlin
sealed class AppError {
    sealed class AuthError : AppError()
    sealed class NetworkError : AppError()
    sealed class ValidationError : AppError()
    data class UnknownError : AppError()
}
```

### 2. ErrorSeverity Enum

```kotlin
enum class ErrorSeverity {
    LOW,    // Inline messages
    MEDIUM, // Snackbars/banners
    HIGH    // Blocking dialogs
}
```

### 3. ErrorMapper Utility

Centralized error mapping for converting Throwables to AppError instances and mapping to user-friendly messages using string resources.

### 4. New UI Components

- `InlineErrorMessage` - For validation errors
- `ErrorBanner` - For medium severity issues
- `showErrorSnackbar` extension - For snackbar presentation

## Migration Examples

### Before (Old System)
```kotlin
// ViewModels would emit UiState.Error with raw Throwable
_uiState.value = UiState.Error(exception)

// UiStateHandler would show blocking dialog for ALL errors
UiStateHandler(
    uiState = uiState,
    content = { data -> /* content */ }
)
```

### After (New System)
```kotlin
// ViewModels can now emit structured AppError states
_uiState.value = AppError.ValidationError.InvalidEmail().asAppErrorState()
_uiState.value = AppError.NetworkError.NoConnection.asAppErrorState()
_uiState.value = AppError.AuthError.SessionExpired.asAppErrorState()

// Or convert Throwables to AppError states
_uiState.value = exception.toAppErrorState(context)

// UiStateHandler now supports different error presentations
UiStateHandler(
    uiState = uiState,
    snackbarHostState = snackbarHostState, // For medium severity errors
    onRetry = { /* retry logic */ },
    content = { data -> /* content */ }
)
```

## Error Severity Mapping

| Error Type | Severity | UI Presentation |
|------------|----------|-----------------|
| ValidationError | LOW | Inline error message |
| NetworkError.NoConnection | MEDIUM | Snackbar |
| NetworkError.Timeout | MEDIUM | Snackbar |
| NetworkError.ApiKeyMissing | MEDIUM | Snackbar |
| NetworkError.ServerError | MEDIUM | Snackbar |
| AuthError.* | HIGH | Blocking dialog |
| UnknownError | HIGH | Blocking dialog |

## Usage Examples

### 1. Validation Errors (Inline Messages)
```kotlin
// In ViewModel
if (email.isBlank()) {
    _uiState.value = AppError.ValidationError.InvalidEmail("Email is required").asAppErrorState()
}

// In Composable - automatically shows inline error message
UiStateHandler(
    uiState = viewModel.uiState,
    content = { data -> /* success content */ }
)
```

### 2. Network Errors (Snackbars)
```kotlin
// In ViewModel
try {
    val data = repository.fetchData()
    _uiState.value = data.asSuccessState()
} catch (e: Exception) {
    _uiState.value = e.toAppErrorState(context)
}

// In Composable - requires SnackbarHostState
val snackbarHostState = remember { SnackbarHostState() }

Scaffold(
    snackbarHost = { SnackbarHost(snackbarHostState) }
) {
    UiStateHandler(
        uiState = viewModel.uiState,
        snackbarHostState = snackbarHostState,
        onRetry = { viewModel.retry() },
        content = { data -> /* success content */ }
    )
}
```

### 3. Critical Errors (Dialogs)
```kotlin
// In ViewModel
if (authToken.isExpired()) {
    _uiState.value = AppError.AuthError.SessionExpired.asAppErrorState()
}

// In Composable - automatically shows blocking dialog
UiStateHandler(
    uiState = viewModel.uiState,
    content = { data -> /* success content */ }
)
```

## Backward Compatibility

The new system maintains backward compatibility:
- Existing `UiState.Error(throwable)` states are automatically converted to AppError
- Old UiStateHandler calls continue to work without modification
- Throwables are mapped to appropriate AppError types using ErrorMapper

## String Resources

All error messages now use string resources for localization:

```xml
<!-- Error messages -->
<string name="error_invalid_credentials">Invalid email or password</string>
<string name="error_no_connection">No internet connection. Please check your network</string>
<string name="error_session_expired">Your session has expired. Please login again</string>
<!-- ... more error strings ... -->
```

## Benefits

1. **Better UX**: Non-blocking error presentation for non-critical issues
2. **Consistency**: Centralized error handling and message mapping
3. **Localization**: All error messages use string resources
4. **Type Safety**: Structured error types instead of raw exceptions
5. **Flexibility**: Different UI patterns based on error severity
6. **Maintainability**: Centralized error logic in ErrorMapper
7. **Backward Compatibility**: Existing code continues to work

## Next Steps

1. Gradually migrate ViewModels to use AppError types instead of raw Throwables
2. Add SnackbarHostState to screens that need medium-severity error handling
3. Customize error messages in string resources for your specific use cases
4. Add retry logic where appropriate using the onRetry callback