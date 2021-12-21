package com.developersbreach.composeactors

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.developersbreach.composeactors.navigation.AppNavigation
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.google.accompanist.insets.ProvideWindowInsets

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ComposeActorsTheme {
                ProvideWindowInsets {
                    AppNavigation()
                }
            }
        }
    }
}