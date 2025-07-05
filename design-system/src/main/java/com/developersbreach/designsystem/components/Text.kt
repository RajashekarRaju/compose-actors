package com.developersbreach.designsystem.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

@Composable
fun CaTextH5(
    text: String,
    modifier: Modifier,
    style: TextStyle = MaterialTheme.typography.h5,
    color: Color = Color.Unspecified
) {
    Text(
        text = text,
        style = style,
        modifier = modifier,
        color = color
    )
}

@Composable
fun CaTextH6(
    text: String,
    modifier: Modifier,
    color: Color = MaterialTheme.colors.onBackground,
    style: TextStyle = MaterialTheme.typography.h6,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    fontWeight: FontWeight? = null
) {
    Text(
        text = text,
        style = style,
        modifier = modifier,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = textAlign,
        color = color,
        fontWeight = fontWeight
    )
}

@Composable
fun CaTextBody1(
    text: String,
    modifier: Modifier,
    style: TextStyle = MaterialTheme.typography.body1,
    color: Color = Color.Unspecified,
) {
    Text(
        text = text,
        style = style,
        modifier = modifier,
        color = color
    )
}

@Composable
fun CaTextButton(
    text: String,
    modifier: Modifier,
    style: TextStyle = MaterialTheme.typography.button
) {
    Text(
        text = text,
        style = style,
        modifier = modifier
    )
}

@Composable
fun CaTextSubtitle2(
    text: String,
    modifier: Modifier,
    style: TextStyle = MaterialTheme.typography.subtitle2,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        style = style,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun CaTextSubtitle1(
    text: String,
    modifier: Modifier,
    style: TextStyle = MaterialTheme.typography.subtitle1,
    color: Color = Color.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    fontWeight: FontWeight? = null,
) {
    Text(
        text = text,
        style = style,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        lineHeight = lineHeight,
        overflow = overflow,
        fontWeight = fontWeight,
    )
}

@Composable
fun CaText(
    text: String,
    modifier: Modifier,
    style: TextStyle,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    Text(
        text = text,
        style = style,
        modifier = modifier,
        maxLines = maxLines,
        overflow = overflow
    )
}