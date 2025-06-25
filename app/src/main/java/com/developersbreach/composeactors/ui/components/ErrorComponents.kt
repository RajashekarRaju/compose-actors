package com.developersbreach.composeactors.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.developersbreach.designsystem.components.CaTextBody2
import com.developersbreach.designsystem.components.CaTextButton

/**
 * Inline error message component for validation errors and low-severity issues
 */
@Composable
fun InlineErrorMessage(
    error: AppError,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val errorMapper = ErrorMapper(context)
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.error.copy(alpha = 0.1f),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = MaterialTheme.colors.error,
                modifier = Modifier.size(20.dp)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                CaTextBody2(
                    text = errorMapper.getErrorMessage(error),
                    color = MaterialTheme.colors.error,
                    modifier = Modifier
                )
            }
            
            // Show retry button if error supports retry
            if (onRetry != null && errorMapper.canRetry(error)) {
                TextButton(
                    onClick = onRetry,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    CaTextButton(
                        text = errorMapper.getRetryActionText(error),
                        color = MaterialTheme.colors.error
                    )
                }
            }
            
            // Show dismiss button if provided
            if (onDismiss != null) {
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    CaTextButton(
                        text = context.getString(R.string.action_dismiss),
                        color = MaterialTheme.colors.error
                    )
                }
            }
        }
    }
}

/**
 * Error banner component for medium-severity issues
 */
@Composable
fun ErrorBanner(
    error: AppError,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val errorMapper = ErrorMapper(context)
    
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colors.error,
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = null,
                tint = MaterialTheme.colors.onError,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = errorMapper.getErrorTitle(error),
                    color = MaterialTheme.colors.onError,
                    style = MaterialTheme.typography.subtitle2,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = errorMapper.getErrorMessage(error),
                    color = MaterialTheme.colors.onError,
                    style = MaterialTheme.typography.body2
                )
            }
            
            // Action buttons
            Row {
                if (onRetry != null && errorMapper.canRetry(error)) {
                    TextButton(
                        onClick = onRetry,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colors.onError
                        )
                    ) {
                        Text(
                            text = errorMapper.getRetryActionText(error),
                            style = MaterialTheme.typography.button
                        )
                    }
                }
                
                if (onDismiss != null) {
                    TextButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colors.onError
                        )
                    ) {
                        Text(
                            text = context.getString(R.string.action_dismiss),
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            }
        }
    }
}

/**
 * Snackbar data class for error presentation
 */
data class ErrorSnackbarData(
    val error: AppError,
    val onRetry: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null
)

/**
 * Extension function to show error snackbar
 */
fun SnackbarHostState.showErrorSnackbar(
    error: AppError,
    context: android.content.Context,
    onRetry: (() -> Unit)? = null,
    actionLabel: String? = null
): kotlinx.coroutines.Job {
    val errorMapper = ErrorMapper(context)
    val message = errorMapper.getErrorMessage(error)
    val action = when {
        onRetry != null && errorMapper.canRetry(error) -> errorMapper.getRetryActionText(error)
        actionLabel != null -> actionLabel
        else -> null
    }
    
    return kotlinx.coroutines.GlobalScope.launch {
        val result = showSnackbar(
            message = message,
            actionLabel = action,
            duration = if (error.getSeverity() == ErrorSeverity.HIGH) 
                SnackbarDuration.Indefinite else SnackbarDuration.Long
        )
        
        if (result == SnackbarResult.ActionPerformed && onRetry != null) {
            onRetry()
        }
    }
}