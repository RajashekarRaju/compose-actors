package com.developersbreach.composeactors.ui.navigation

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.developersbreach.composeactors.MainActivity
import com.developersbreach.composeactors.domain.session.GetSessionState
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(com.developersbreach.composeactors.di.AuthenticationServiceModule::class)
class AppNavigationUITest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    val authenticationService = mockAuthService()

    @BindValue
    val getSessionState: GetSessionState = mockGetSessionState(authenticationService)

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            ComposeActorsTheme {
                AppNavigation(
                    navController = navController,
                    startDestination = AppDestinations.Splash,
                )
            }
        }
    }

    @Test
    fun splash_to_login_flow() {
        composeTestRule
            .onNodeWithTag("TestTag:SplashScreen")
            .assertIsDisplayed()

        composeTestRule.waitUntil(5_000L) {
            composeTestRule
                .onAllNodesWithTag("TestTag:LoginScreen")
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeTestRule
            .onNodeWithTag("TestTag:LoginScreen")
            .assertIsDisplayed()
    }
}