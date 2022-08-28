package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.model.Cast
import com.developersbreach.composeactors.data.model.Genre
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.ui.components.CategoryTitle
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import kotlinx.coroutines.Job

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetailsContent(
    uiState: MovieDetailUiState,
    navigateUp: () -> Unit,
    showFab: MutableState<Boolean>,
    viewModel: MovieDetailViewModel,
    openMovieDetailsBottomSheet: () -> Job
) {
    val movieData = uiState.movieData
    val listState = rememberLazyListState()

    // Change top bar background color on user scrolls, to make foreground details visible.
    val showTopBarBackground = rememberSaveable { mutableStateOf(false) }
    showTopBarBackground.value = listState.firstVisibleItemScrollOffset > 0

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
            MovieImageBanner(movieData?.banner)
            Spacer(modifier = Modifier.height(16.dp))
            MovieOverviewText(movieData?.overview)
            Spacer(modifier = Modifier.height(16.dp))
            CategoryTitle(title = "Cast", alpha = 1f)
            Spacer(modifier = Modifier.height(16.dp))
            GetMovieCast(uiState.movieCast, viewModel, openMovieDetailsBottomSheet)
            Spacer(modifier = Modifier.height(24.dp))
            CategoryTitle(title = "Similar", alpha = 1f)
            GetRelatedMovies(movieList = uiState.similarMovies)
            Spacer(modifier = Modifier.height(12.dp))
            CategoryTitle(title = "Recommended", alpha = 1f)
            GetRelatedMovies(movieList = uiState.recommendedMovies)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun MovieGenre(
    genres: List<Genre>?
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        if (genres != null) {
            items(genres) { genre ->
                Card(
                    shape = RoundedCornerShape(24.dp),
                    backgroundColor = MaterialTheme.colors.surface,
                ) {
                    Text(
                        text = genre.genreName,
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun GetMovieCast(
    movieCast: List<Cast>,
    viewModel: MovieDetailViewModel,
    openMovieDetailsBottomSheet: () -> Job
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(
            items = movieCast,
            key = { it.actorId }
        ) { cast ->
            ItemCast(
                cast = cast,
                viewModel = viewModel,
                openMovieDetailsBottomSheet = openMovieDetailsBottomSheet
            )
        }
    }
}

@Composable
private fun ItemCast(
    cast: Cast,
    viewModel: MovieDetailViewModel,
    openMovieDetailsBottomSheet: () -> Job
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(120.dp)
            .clickable {
                viewModel.getSelectedActorDetails(cast.actorId)
                openMovieDetailsBottomSheet()
            }
    ) {
        LoadNetworkImage(
            imageUrl = cast.castProfilePath,
            contentDescription = stringResource(R.string.cd_actor_image),
            shape = CircleShape,
            modifier = Modifier
                .size(120.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                    shape = CircleShape
                )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = cast.castName,
            style = MaterialTheme.typography.subtitle2,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        )
    }
}

@Composable
private fun MovieOverviewText(
    overview: String?
) {
    var expanded by remember { mutableStateOf(false) }

    Text(
        text = overview.toString(),
        maxLines = if (expanded) Int.MAX_VALUE else 4,
        overflow = if (expanded) TextOverflow.Visible else TextOverflow.Ellipsis,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable { expanded = !expanded },
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