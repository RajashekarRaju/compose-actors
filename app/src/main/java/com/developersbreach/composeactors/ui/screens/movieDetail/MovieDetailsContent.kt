package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.ui.components.CategoryTitle
import com.developersbreach.composeactors.ui.screens.movieDetail.composables.GetMovieCast
import com.developersbreach.composeactors.ui.screens.movieDetail.composables.GetRelatedMovies
import com.developersbreach.composeactors.ui.screens.movieDetail.composables.MovieDetailImageBanner
import com.developersbreach.composeactors.ui.screens.movieDetail.composables.MovieDetailOverviewText
import com.developersbreach.composeactors.ui.screens.movieDetail.composables.MovieGenre
import kotlinx.coroutines.Job

@Composable
fun MovieDetailsContent(
    modifier: Modifier = Modifier,
    data: MovieDetailsUiState,
    navigateUp: () -> Unit,
    showFab: MutableState<Boolean>,
    openMovieDetailsBottomSheet: () -> Job,
    selectBottomSheetCallback: (BottomSheetType) -> Unit,
    showBottomSheetScaffold: MutableState<Boolean>,
) {
    val movieData = data.movieData
    val listState = rememberLazyListState()

    // Change top bar background color on user scrolls, to make foreground details visible.
    val showTopBarBackground = remember {
        derivedStateOf { listState.firstVisibleItemScrollOffset > 0 }
    }

    LaunchedEffect(showTopBarBackground.value) {
        showBottomSheetScaffold.value = !showTopBarBackground.value
    }

    // Hide fab with animation when user scrolls the lazy list
    showFab.value = !listState.isScrollInProgress

    LazyColumn(
        state = listState,
        modifier = modifier.navigationBarsPadding(),
    ) {
        stickyHeader {
            MovieDetailsTopAppBar(
                modifier = modifier.testTag("TestTag:MovieDetailsTopAppBar"),
                navigateUp = navigateUp,
                title = movieData?.movieTitle,
                showTopBarBackground = showTopBarBackground,
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            MovieGenre(movieData?.genres)
            Spacer(modifier = Modifier.height(16.dp))
            MovieDetailImageBanner(movieData?.backdropUrl)
            Spacer(modifier = Modifier.height(16.dp))
            MovieDetailOverviewText(movieData?.overview)
            Spacer(modifier = Modifier.height(16.dp))
            CategoryTitle(title = "Cast", alpha = 1f)
            Spacer(modifier = Modifier.height(16.dp))
            GetMovieCast(
                data = data,
                openMovieDetailsBottomSheet = openMovieDetailsBottomSheet,
                selectBottomSheetCallback = selectBottomSheetCallback,
            )
            Spacer(modifier = Modifier.height(24.dp))
            CategoryTitle(title = "Similar", alpha = 1f)
            GetRelatedMovies(
                movieList = data.similarMovies,
                openMovieDetailsBottomSheet = openMovieDetailsBottomSheet,
                selectBottomSheetCallback = selectBottomSheetCallback,
            )
            Spacer(modifier = Modifier.height(12.dp))
            CategoryTitle(title = "Recommended", alpha = 1f)
            GetRelatedMovies(
                movieList = data.recommendedMovies,
                openMovieDetailsBottomSheet = openMovieDetailsBottomSheet,
                selectBottomSheetCallback = selectBottomSheetCallback,
            )
            Spacer(modifier = Modifier.height(52.dp))
        }
    }
}