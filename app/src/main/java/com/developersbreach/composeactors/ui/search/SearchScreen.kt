package com.developersbreach.composeactors.ui.search


import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.google.accompanist.insets.systemBarsPadding


@Composable
fun CategoryAppBar(
    navigateUp: () -> Unit,
    titles: String
) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    tint = MaterialTheme.colors.onBackground,
                    contentDescription = null
                )
            }

            Text(
                text = titles,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun SearchScreen(
    selectedActor: (Int) -> Unit,
    navigateUp: () -> Unit,
    viewModel: SearchViewModel
) {

    val searchableActors by viewModel.searchUiState.observeAsState(listOf())

    Surface(
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 8.dp)
        ) {
            CategoryAppBar(
                navigateUp = navigateUp,
                "Robert Downey Jr"
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(searchableActors) { actor ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = actor.actorName,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                            .clickable(onClick = { selectedActor(actor.actorId) })
                    )
                }
            }
        }
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun SearchScreenPreview() {
    ComposeActorsTheme(darkTheme = true) {

        Surface(
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .padding(horizontal = 8.dp)
            ) {
                CategoryAppBar(
                    navigateUp = { },
                    "Robert Downey Jr"
                )
            }
        }
    }
}