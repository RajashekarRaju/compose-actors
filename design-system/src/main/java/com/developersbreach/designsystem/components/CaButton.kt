package com.developersbreach.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CaButtonFilled(
    modifier: Modifier,
    title: String,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.primary,
    )
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = colors,
        content = {
            CaTextSubtitle1(
                text = title,
                modifier = Modifier.padding(vertical = 2.dp),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.onPrimary
            )
        }
    )
}

@Composable
fun CaButtonText(
    modifier: Modifier,
    title: String,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        content = {
            CaTextSubtitle1(
                text = title,
                modifier = Modifier,
                fontWeight = FontWeight.Medium
            )
        }
    )
}

@Composable
fun CaButtonOutlined(
    modifier: Modifier,
    title: String,
    onClick: () -> Unit,
    borderStroke: BorderStroke = BorderStroke(
        width = 2.dp,
        color = MaterialTheme.colors.primary
    ),
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        backgroundColor = Color.Transparent,
    )
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        colors = colors,
        border = borderStroke,
        content = {
            CaTextSubtitle1(
                text = title,
                modifier = Modifier.padding(vertical = 2.dp),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.primary
            )
        }
    )
}