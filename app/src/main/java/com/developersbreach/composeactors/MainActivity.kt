package com.developersbreach.composeactors

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.developersbreach.composeactors.ui.navigation.AppNavigation
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ComposeActorsTheme {
                AppNavigation()
            }
        }
    }
}