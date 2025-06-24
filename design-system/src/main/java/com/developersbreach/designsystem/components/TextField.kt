package com.developersbreach.designsystem.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

data class CaTextFieldIconConfig(
    val modifier: Modifier,
    val iconModifier: Modifier,
    val onClick: () -> Unit = { },
    val imageVector: ImageVector,
    val tint: Color,
    val contentDescription: String? = null
)

@Composable
fun CaTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIconInfo: CaTextFieldIconConfig,
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
        colors = colors,
    )
}

@Composable
fun CaOutlinedTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: CaTextFieldIconConfig? = null,
    trailingIcon: CaTextFieldIconConfig? = null,
    placeholderText: String? = null,
    label: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.subtitle1,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text
    ),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        backgroundColor = MaterialTheme.colors.surface,
        textColor = MaterialTheme.colors.onSurface,
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            if (leadingIcon != null) {
                CaIconButton(
                    modifier = leadingIcon.modifier,
                    iconModifier = leadingIcon.iconModifier,
                    onClick = { leadingIcon.onClick() },
                    imageVector = leadingIcon.imageVector,
                    tint = leadingIcon.tint,
                    contentDescription = leadingIcon.contentDescription,
                )
            }
        },
        trailingIcon = {
            if (trailingIcon != null) {
                CaIconButton(
                    modifier = trailingIcon.modifier,
                    iconModifier = trailingIcon.iconModifier,
                    onClick = { trailingIcon.onClick() },
                    imageVector = trailingIcon.imageVector,
                    tint = trailingIcon.tint,
                    contentDescription = trailingIcon.contentDescription
                )
            }
        },
        placeholder = {
            if (placeholderText != null) {
                CaTextSubtitle1(
                    text = placeholderText,
                    modifier = Modifier,
                )
            }
        },
        textStyle = textStyle,
        singleLine = singleLine,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        colors = colors,
        interactionSource = interactionSource,
        visualTransformation = visualTransformation,
    )
}
