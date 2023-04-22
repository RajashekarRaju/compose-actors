package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.data.model.BottomSheetType
import com.developersbreach.composeactors.ui.components.CategoryTitle
import com.developersbreach.composeactors.ui.screens.movieDetail.composables.*
import kotlinx.coroutines.Job

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetailsContent(
    uiState: MovieDetailsUIState,
    navigateUp: () -> Unit,
    showFab: MutableState<Boolean>,
    openMovieDetailsBottomSheet: () -> Job,
    selectBottomSheetCallback: (BottomSheetType) -> Unit
) {
    val movieData = uiState.movieData
    val listState = rememberLazyListState()

    // Change top bar background color on user scrolls, to make foreground details visible.
    val showTopBarBackground = remember {
        derivedStateOf { listState.firstVisibleItemScrollOffset > 0 }
    }

    // Hide fab with animation when user scrolls the lazy list
    showFab.value = !listState.isScrollInProgress

    LazyColumn(
        state = listState,
        modifier = Modifier.navigationBarsPadding()
    ) {

        stickyHeader {
            MovieDetailTopAppBar(
                navigateUp = navigateUp,
                title = movieData?.movieTitle,
                showTopBarBackground = showTopBarBackground
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            MovieGenre(movieData?.genres)
            Spacer(modifier = Modifier.height(16.dp))
            MovieDetailImageBanner(movieData?.banner)
            Spacer(modifier = Modifier.height(16.dp))
            MovieDetailOverviewText(movieData?.overview)
            Spacer(modifier = Modifier.height(16.dp))
            CategoryTitle(title = "Cast", alpha = 1f)
            Spacer(modifier = Modifier.height(16.dp))
            GetMovieCast(
                uiState = uiState,
                openMovieDetailsBottomSheet = openMovieDetailsBottomSheet,
                selectBottomSheetCallback = selectBottomSheetCallback
            )
            Spacer(modifier = Modifier.height(24.dp))
            CategoryTitle(title = "Similar", alpha = 1f)
            GetRelatedMovies(
                movieList = uiState.similarMovies,
                openMovieDetailsBottomSheet = openMovieDetailsBottomSheet,
                selectBottomSheetCallback = selectBottomSheetCallback
            )
            Spacer(modifier = Modifier.height(12.dp))
            CategoryTitle(title = "Recommended", alpha = 1f)
            GetRelatedMovies(
                movieList = uiState.recommendedMovies,
                openMovieDetailsBottomSheet = openMovieDetailsBottomSheet,
                selectBottomSheetCallback = selectBottomSheetCallback
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}