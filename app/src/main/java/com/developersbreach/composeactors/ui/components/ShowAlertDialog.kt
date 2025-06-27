package com.developersbreach.composeactors.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.window.DialogProperties
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaTextBody1
import com.developersbreach.designsystem.components.CaTextButton
import com.developersbreach.designsystem.components.CaTextH5

@Composable
fun ShowAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    isDismissible: Boolean,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        title = {
            CaTextH5(
                text = title,
                modifier = Modifier,
            )
        },
        text = {
            CaTextBody1(
                text = description,
                modifier = Modifier,
            )
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(
                    onClick = { if (isDismissible) onDismissRequest() },
                    modifier = Modifier,
                    shape = MaterialTheme.shapes.medium,
                    content = {
                        CaTextButton(
                            text = "Dismiss",
                            modifier = Modifier,
                        )
                    },
                )
            }
        },
        onDismissRequest = { if (isDismissible) onDismissRequest() },
        modifier = modifier.testTag("TestTag:InfoDialog"),
        properties = DialogProperties(
            dismissOnBackPress = isDismissible,
            dismissOnClickOutside = isDismissible,
        ),
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface,
    )
}

@PreviewLightDark
@Composable
private fun ShowAlertDialogUIPreview(
    @PreviewParameter(LoremIpsum::class) text: String,
) {
    ComposeActorsTheme {
        ShowAlertDialog(
            title = "Error occurred",
            description = text,
            isDismissible = false,
            onDismissRequest = {},
        )
    }
}