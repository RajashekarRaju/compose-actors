package com.developersbreach.designsystem.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType

data class CaTextFieldLeadingIconConfig(
    val modifier: Modifier,
    val iconModifier: Modifier,
    val onClick: () -> Unit,
    val imageVector: ImageVector,
    val tint: Color,
    val contentDescription: String?
)

@Composable
fun CaTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIconInfo: CaTextFieldLeadingIconConfig,
    trailingIcon: @Composable (() -> Unit)? = null,
    placeholderText: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.subtitle1,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text
    ),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        backgroundColor = Color.Transparent
    )
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            CaIconButton(
                modifier = leadingIconInfo.modifier,
                iconModifier = leadingIconInfo.iconModifier,
                onClick = {leadingIconInfo.onClick()},
                imageVector = leadingIconInfo.imageVector,
                tint = leadingIconInfo.tint,
                contentDescription = leadingIconInfo.contentDescription
            )
        },
        trailingIcon = trailingIcon,
        placeholder = {
            Text(text = placeholderText ?: "")
        },
        textStyle = textStyle,
        singleLine = singleLine,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        colors = colors
    )
}
