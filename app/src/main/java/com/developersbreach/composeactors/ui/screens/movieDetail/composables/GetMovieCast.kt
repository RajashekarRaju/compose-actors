package com.developersbreach.composeactors.ui.screens.movieDetail.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.data.datasource.fake.fakeMovieDetail
import com.developersbreach.composeactors.data.movie.model.Cast
import com.developersbreach.composeactors.data.movie.model.Flatrate
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.screens.movieDetail.BottomSheetType
import com.developersbreach.composeactors.ui.screens.movieDetail.MovieDetailsData
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaTextSubtitle2
import kotlinx.coroutines.Job

@Composable
fun GetMovieCast(
    data: MovieDetailsData,
    openMovieDetailsBottomSheet: () -> Job,
    selectBottomSheetCallback: (BottomSheetType) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(
            items = data.movieCast,
            key = { it.personId }
        ) { cast ->
            ItemCast(
                cast = cast,
                data = data,
                openMovieDetailsBottomSheet = openMovieDetailsBottomSheet,
                selectBottomSheetCallback = selectBottomSheetCallback
            )
        }
    }
}

@Composable
private fun ItemCast(
    cast: Cast,
    data: MovieDetailsData,
    openMovieDetailsBottomSheet: () -> Job,
    selectBottomSheetCallback: (BottomSheetType) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(120.dp)
            .clickable {
                openMovieDetailsBottomSheet()
                selectBottomSheetCallback(
                    BottomSheetType.ActorDetailBottomSheet.apply {
                        movieOrPersonId = cast.personId
                    }
                )
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

        CaTextSubtitle2(
            text = cast.castName,
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

@PreviewLightDark
@Composable
private fun GetMovieCastPreview() {
    ComposeActorsTheme {
        GetMovieCast(
            data = MovieDetailsData(
                movieData = fakeMovieDetail,
                similarMovies = listOf(),
                recommendedMovies = listOf(),
                movieCast = listOf(),
                isFetchingDetails = false,
                movieProviders = listOf(Flatrate("", 1, ""))
            ),
            openMovieDetailsBottomSheet = { Job() },
            selectBottomSheetCallback = { }
        )
    }
}