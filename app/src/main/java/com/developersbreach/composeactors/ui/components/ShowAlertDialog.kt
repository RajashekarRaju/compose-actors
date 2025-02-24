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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.window.DialogProperties
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaTextBody1
import com.developersbreach.designsystem.components.CaTextButton
import com.developersbreach.designsystem.components.CaTextH5

@Composable
fun ShowAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    onButtonClick: () -> Unit = {},
) {
    AlertDialog(
        title = {
            CaTextH5(
                text = title,
                modifier = Modifier
            )
        },
        text = {
            CaTextBody1(
                text = description,
                modifier = Modifier
            )
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {},
                    modifier = Modifier,
                    shape = MaterialTheme.shapes.medium,
                    content = {
                        CaTextButton(
                            text = "Dismiss",
                            modifier = Modifier
                        )
                    }
                )
            }
        },
        onDismissRequest = onButtonClick,
        modifier = modifier.testTag("TestTag:InfoDialog"),
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF211a18)
@Composable
private fun ShowAlertDialogUIDarkPreview(
    @PreviewParameter(LoremIpsum::class) text: String
) {
    ComposeActorsTheme(darkTheme = true) {
        ShowAlertDialog(
            title = "Error occurred",
            description = text,
            onButtonClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ShowAlertDialogUILightPreview(
    @PreviewParameter(LoremIpsum::class) text: String
) {
    ComposeActorsTheme(darkTheme = true) {
        ShowAlertDialog(
            title = "Error occurred",
            description = text,
            onButtonClick = {}
        )
    }
}