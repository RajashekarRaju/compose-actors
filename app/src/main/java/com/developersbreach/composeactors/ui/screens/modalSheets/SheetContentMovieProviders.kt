package com.developersbreach.composeactors.ui.screens.modalSheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.movie.model.Flatrate
import com.developersbreach.designsystem.components.CaDivider
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.screens.movieDetail.composables.FloatingAddToWatchlistButton
import com.developersbreach.designsystem.components.CaTextBody1
import com.developersbreach.designsystem.components.CaTextH6

@Composable
fun SheetContentMovieProviders(
    movieProvider: List<Flatrate>,
    isInWatchlist: Boolean,
    addToWatchlist: () -> Unit,
    removeFromWatchlist: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(MaterialTheme.colors.surface)
            .navigationBarsPadding(),
    ) {
        val (header, streaming, noStreaming, floatingButton) = createRefs()

        HeaderModalSheet(
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top, margin = 16.dp)
            },
        )

        if (movieProvider.isNotEmpty()) {
            Streaming(
                flatrate = movieProvider,
                modifier = Modifier.constrainAs(streaming) {
                    top.linkTo(header.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 24.dp)
                    end.linkTo(parent.end)
                },
            )
        } else {
            NoStreaming(
                modifier = Modifier.constrainAs(noStreaming) {
                    top.linkTo(header.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            )
        }

        FloatingAddToWatchlistButton(
            isInWatchlist = isInWatchlist,
            addToWatchlist = addToWatchlist,
            removeFromWatchlist = removeFromWatchlist,
            modifier = Modifier.constrainAs(floatingButton) {
                bottom.linkTo(parent.bottom, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
        )
    }
}

@Composable
fun NoStreaming(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CaTextBody1(
            text = stringResource(id = R.string.no_watch_options),
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier,
        )
    }
}

@Composable
fun Streaming(
    flatrate: List<Flatrate>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp),
    ) {
        items(flatrate) { flatrate ->
            LoadNetworkImage(
                imageUrl = flatrate.logo,
                contentDescription = "",
                shape = MaterialTheme.shapes.large,
                showAnimProgress = false,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(48.dp),
            )
        }
    }
}

@Composable
private fun HeaderModalSheet(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            CaDivider(
                thickness = 4.dp,
                modifier = Modifier
                    .clip(RoundedCornerShape(percent = 100))
                    .width(48.dp),
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        CaTextH6(
            text = stringResource(id = R.string.stream),
            modifier = Modifier,
        )
    }
}