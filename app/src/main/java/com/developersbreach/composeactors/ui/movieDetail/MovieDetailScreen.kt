package com.developersbreach.composeactors.ui.movieDetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.model.MovieDetail
import com.developersbreach.composeactors.ui.components.LoadNetworkImage

@Composable
fun MovieDetailScreen(
    selectedMovie: (Int) -> Unit,
    viewModel: MovieDetailViewModel,
    navigateUp: () -> Unit
) {
    val uiState = viewModel.uiState

    Surface(
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = {
                MovieDetailAppBar(
                    navigateUp = navigateUp,
                    title = uiState.movieData?.movieTitle
                )
            }
        ) {
            MovieDetailsContent(uiState.movieData)
        }
    }
}

@Composable
fun MovieDetailsContent(movieData: MovieDetail?) {
    LazyColumn {
        item { MovieBanner(movieData?.banner) }
    }
}

@Composable
private fun MovieBanner(
    bannerUrl: String?
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LoadNetworkImage(
            imageUrl = "$bannerUrl",
            contentDescription = stringResource(id = R.string.cd_actor_image),
            shape = RectangleShape,
            modifier = Modifier.fillMaxWidth().requiredHeight(300.dp)
        )
    }
}
