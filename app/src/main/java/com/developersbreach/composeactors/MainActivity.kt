package com.developersbreach.composeactors

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.developersbreach.composeactors.navigation.AppNavigation
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeActorsTheme {
                AppNavigation()
            }
        }
    }
}