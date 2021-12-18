package com.developersbreach.composeactors.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.developersbreach.composeactors.R

// Set of Material typography styles to start with
private val MontserratAlternates = FontFamily(
    Font(R.font.montserrat_alternates_light, FontWeight.Light),
    Font(R.font.montserrat_alternates_regular, FontWeight.Normal),
    Font(R.font.montserrat_alternates_medium, FontWeight.Medium),
    Font(R.font.montserrat_alternates_semibold, FontWeight.SemiBold),
    Font(R.font.montserrat_alternates_bold, FontWeight.Bold),
)

// Material typography styles
val Typography = Typography(

    h3 = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W800,
        fontSize = 32.sp
    ),
    h4 = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W700,
        fontSize = 32.sp
    ),
    h5 = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        fontFamily = MontserratAlternates,
        fontSize = 14.sp
    ),
)