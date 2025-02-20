package com.developersbreach.composeactors.ui.screens.search


import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.components.KeyboardState
import com.developersbreach.composeactors.ui.components.closeKeyboardAndNavigateUp
import com.developersbreach.composeactors.ui.components.getCurrentKeyboardState
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaDivider
import com.developersbreach.designsystem.components.CaIconButton

/**
 * @param navigateUp Navigates to previous screen.
 *
 * onValueChange = { onQueryChanged ->
 *     query = onQueryChanged
 *     if (onQueryChanged.isNotEmpty()) {
 *     viewModel.performQuery(onQueryChanged)
 * }
 *
 * If user makes changes to text, immediately updated it.
 * To avoid crash, only query when string isn't empty.
 * Pass latest query to refresh search results.
 */
@Composable
fun SearchAppBar(
    navigateUp: () -> Unit,
    onQueryChange: (searchQuery: String) -> Unit,
    searchHint: String,
    closeKeyboard: () -> Unit?
) {
    // Immediately update and keep track of query from text field changes.
    var query: String by rememberSaveable { mutableStateOf("") }

    // Handle clear icon state whether to show or hide based on query.
    var showClearQueryIcon: Boolean by rememberSaveable { mutableStateOf(false) }
    // Initially the icon state will be false and hidden since query will be empty.
    if (query.isEmpty()) {
        // If query is not empty show the icon
        showClearQueryIcon = false
    } else if (query.isNotEmpty()) {
        // If query is empty hide the icon
        showClearQueryIcon = true
    }

    // Detects whether a current keyboard is opened or closed
    val keyboardState: KeyboardState by getCurrentKeyboardState()

    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    val resultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        val recordedSpeech = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
        if (!recordedSpeech.isNullOrEmpty()) {
            query = recordedSpeech[0]
            // Perform query with the recorded query string.
            onQueryChange(query)
        }
    }

    Column {

        // This Spacer avoids colliding content with app bar by matching the height of status bar.
        Spacer(Modifier.statusBarsPadding())

        TextField(
            value = query,
            onValueChange = { newQuery ->
                // If user makes changes to text, immediately updated it.
                query = newQuery
                // To avoid crash, only query when string isn't empty.
                if (newQuery.isNotEmpty()) {
                    // Pass latest query to refresh search results.
                    onQueryChange(newQuery)
                }
            },
            leadingIcon = {
                CaIconButton(
                    modifier = Modifier.padding(start = 4.dp),
                    onClick = {
                        closeKeyboardAndNavigateUp(
                            navigateUp = navigateUp,
                            closeKeyboard = closeKeyboard,
                            keyboardState = keyboardState
                        )
                    },
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    tint = MaterialTheme.colors.onBackground,
                    contentDescription = stringResource(id = R.string.cd_search_icon)
                )
            },
            trailingIcon = {
                if (showClearQueryIcon) {
                    CaIconButton(
                        onClick = {
                            query = ""
                            closeKeyboard()
                        },
                        modifier = Modifier,
                        imageVector = Icons.Rounded.Clear,
                        tint = MaterialTheme.colors.onBackground,
                        contentDescription = stringResource(id = R.string.cd_clear_icon)
                    )
                } else {
                    CaIconButton(
                        modifier = Modifier,
                        onClick = {
                            // This starts the activity and populates the intent with the speech text.
                            resultLauncher.launch(createLaunchSpeechRecognitionIntent)
                        },
                        painter = painterResource(id = R.drawable.ic_mic),
                        tint = MaterialTheme.colors.onBackground,
                        contentDescription = "",
                    )
                }
            },
            placeholder = { Text(text = searchHint) },
            textStyle = MaterialTheme.typography.subtitle1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background, RectangleShape),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent
            )
        )

        // Divides content and search bar with line.
        CaDivider(
            modifier = Modifier,
            colorAlpha = 0.1f
        )
    }
}

// Create an intent that can start the Speech Recognizer activity
private val createLaunchSpeechRecognitionIntent = Intent(
    RecognizerIntent.ACTION_RECOGNIZE_SPEECH
).apply {
    putExtra(
        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
    )
}

@Preview
@Composable
private fun SearchAppBarLightPreview() {
    ComposeActorsTheme(darkTheme = true) {
        SearchAppBar(
            navigateUp = { },
            onQueryChange = { },
            searchHint = stringResource(id = R.string.hint_search_query_actors),
            closeKeyboard = { }
        )
    }
}

@Preview
@Composable
private fun SearchAppBarDarkPreview() {
    ComposeActorsTheme(darkTheme = false) {
        SearchAppBar(
            navigateUp = { },
            onQueryChange = { },
            searchHint = stringResource(id = R.string.hint_search_query_movies),
            closeKeyboard = { }
        )
    }
}