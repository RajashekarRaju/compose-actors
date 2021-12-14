package com.developersbreach.composeactors

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.developersbreach.composeactors.navigation.AppNavigation
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.google.accompanist.insets.ProvideWindowInsets

class MainActivity : AppCompatActivity() {

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