package com.developersbreach.composeactors.ui.search


import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.developersbreach.composeactors.ui.actors.ActorsViewModel
import com.developersbreach.composeactors.ui.actors.SearchBar
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme


@Preview(widthDp = 360, heightDp = 640)
@Composable
fun SearchScreenPreview() {
    ComposeActorsTheme(darkTheme = true) {
        SearchScreen({ }, viewModel())
    }
}

@Composable
fun SearchScreen(
    selectedActor: (Int) -> Unit,
    viewModel: ActorsViewModel
) {
    Surface(
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    { SearchBar() },
                    backgroundColor = MaterialTheme.colors.background,
                    elevation = 0.dp
                )
            }
        ) {

        }
    }
}
