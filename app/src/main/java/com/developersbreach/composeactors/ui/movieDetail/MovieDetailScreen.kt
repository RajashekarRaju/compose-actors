package com.developersbreach.composeactors.ui.movieDetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.model.Movie
import com.developersbreach.composeactors.ui.components.CategoryTitle
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.utils.LayerRevealImage

@Composable
fun MovieDetailScreen(
    selectedMovie: (Int) -> Unit,
    viewModel: MovieDetailViewModel,
    navigateUp: () -> Unit
) {
    val uiState = viewModel.uiState
    val isLayerRevealAnimationEnded = rememberSaveable { mutableStateOf(false) }
    val showFab = rememberSaveable { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background poster with layer reveal effect
        LayerRevealImage(uiState.movieData?.poster, isLayerRevealAnimationEnded)
        // Fade enter animation detail screen once layer reveal completes
        if (isLayerRevealAnimationEnded.value) {
            AnimateDetailScreenContent(uiState, navigateUp, showFab)
        }
        // Progress bar- Hidden temporarily, although it works fine cannot have it in current screen
        // placement since it is on to of reveal animation.
        // ShowProgressIndicator(isLoadingData = uiState.isFetchingDetails)
        if (showFab.value) {
            FloatingDialPadButton { }
        }
    }
}

@Composable
private fun AnimateDetailScreenContent(
    uiState: MovieDetailUiState,
    navigateUp: () -> Unit,
    showFab: MutableState<Boolean>
) {
    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn()
    ) {
        // Main details content
        MovieDetailsContent(uiState, navigateUp, showFab)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MovieDetailsContent(
    uiState: MovieDetailUiState,
    navigateUp: () -> Unit,
    showFab: MutableState<Boolean>
) {
    val movieData = uiState.movieData
    val listState = rememberLazyListState()

    showFab.value = !listState.isScrollInProgress

    LazyColumn(
        state = listState,
        modifier = Modifier.navigationBarsPadding()
    ) {

        stickyHeader {
            MovieDetailAppBar(
                navigateUp = navigateUp,
                title = movieData?.movieTitle
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            MovieImageBanner(movieData?.banner)
            Spacer(modifier = Modifier.height(12.dp))
            MovieOverviewText(movieData?.overview)
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            CategoryTitle(title = "Similar", alpha = 1f)
            GetRelatedMovies(movieList = uiState.similarMovies)
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            CategoryTitle(title = "Recommended", alpha = 1f)
            GetRelatedMovies(movieList = uiState.recommendedMovies)
            Spacer(modifier = Modifier.padding(vertical = 28.dp))
        }
    }
}

@Composable
private fun MovieOverviewText(
    overview: String?
) {
    Text(
        text = overview.toString(),
        maxLines = 8,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        style = TextStyle(
            lineHeight = 20.sp,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Justify,
            fontSize = 16.sp
        )
    )
}

@Composable
private fun MovieImageBanner(
    bannerUrl: String?
) {
    LoadNetworkImage(
        imageUrl = bannerUrl.toString(),
        contentDescription = "",
        shape = MaterialTheme.shapes.large,
        showAnimProgress = false,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .aspectRatio(1.8f)
            .shadow(elevation = 8.dp),
    )
}

@Composable
private fun GetRelatedMovies(
    movieList: List<Movie>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            items = movieList,
            key = { it.movieId }
        ) { movie ->
            LoadNetworkImage(
                imageUrl = movie.posterPathUrl,
                contentDescription = stringResource(R.string.cd_movie_poster),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.size(100.dp, 150.dp)
            )
        }
    }
}
