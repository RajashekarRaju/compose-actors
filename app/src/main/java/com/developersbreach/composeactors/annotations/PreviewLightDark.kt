package com.developersbreach.composeactors.annotations

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    backgroundColor = 0xFF211a18,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class PreviewLightDark