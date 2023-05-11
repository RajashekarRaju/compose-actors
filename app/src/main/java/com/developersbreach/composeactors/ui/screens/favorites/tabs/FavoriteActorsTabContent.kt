package com.developersbreach.composeactors.ui.screens.favorites.tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.datasource.fake.fakeFavoriteActorsList
import com.developersbreach.composeactors.data.model.FavoriteActor
import com.developersbreach.composeactors.ui.components.ImageBackgroundThemeGenerator
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.components.verticalGradientScrim
import com.developersbreach.composeactors.ui.screens.favorites.NoFavoritesFoundUI
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.composeactors.utils.getPlaceOfBirth

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteActorsTabContent(
    navigateToSelectedActor: (Int) -> Unit,
    favoriteActors: List<FavoriteActor>,
    removeFavoriteActor: (FavoriteActor) -> Unit,
) {
    if (favoriteActors.isEmpty()) {
        NoFavoritesFoundUI()
    }

    val listState = rememberPagerState()

    VerticalPager(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        pageCount = favoriteActors.size,
        pageSpacing = 24.dp,
        pageSize = PageSize.Fixed(512.dp),
        contentPadding = PaddingValues(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 48.dp),
    ) { currentPage ->
        ItemFavoriteActor(
            actorItem = favoriteActors[currentPage],
            onClickActor = navigateToSelectedActor,
            removeFavoriteActor = removeFavoriteActor
        )
    }
}

@Composable
private fun ItemFavoriteActor(
    actorItem: FavoriteActor,
    onClickActor: (Int) -> Unit,
    removeFavoriteActor: (FavoriteActor) -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .clip(MaterialTheme.shapes.large)
    ) {
        LoadNetworkImage(
            imageUrl = actorItem.profileUrl,
            contentDescription = "Actor Image",
            shape = RectangleShape,
            showAnimProgress = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(512.dp)
                .clickable {
                    onClickActor(actorItem.actorId)
                }
        )

        ImageBackgroundThemeGenerator(
            imageUrl = actorItem.profileUrl,
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(124.dp)
                    .verticalGradientScrim(
                        color = MaterialTheme.colors.primary.copy(alpha = 0.75f),
                        startYPercentage = 0f,
                        endYPercentage = 1f
                    )
            )
        }

        ImageBackgroundThemeGenerator(
            imageUrl = actorItem.profileUrl,
            backgroundColor = MaterialTheme.colors.onSurface
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 20.dp, bottom = 20.dp, end = 12.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = actorItem.actorName,
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Text(
                        text = "${getPlaceOfBirth(actorItem.placeOfBirth)}",
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onPrimary,
                    )
                }

                IconButton(
                    onClick = { removeFavoriteActor(actorItem) },
                    modifier = Modifier
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_favorite),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteActorsTabContentPreviewLightPreview() {
    ComposeActorsTheme(darkTheme = false) {
        FavoriteActorsTabContent(
            navigateToSelectedActor = {},
            favoriteActors = fakeFavoriteActorsList(),
            removeFavoriteActor = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF211a18)
@Composable
private fun FavoriteActorsTabContentPreviewDarkPreview() {
    ComposeActorsTheme(darkTheme = true) {
        FavoriteActorsTabContent(
            navigateToSelectedActor = {},
            favoriteActors = fakeFavoriteActorsList(),
            removeFavoriteActor = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteActorsTabContentNoFavoritesPreviewLightPreview() {
    ComposeActorsTheme(darkTheme = false) {
        FavoriteActorsTabContent(
            navigateToSelectedActor = {},
            favoriteActors = emptyList(),
            removeFavoriteActor = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF211a18)
@Composable
private fun FavoriteActorsTabContentNoFavoritesPreviewDarkPreview() {
    ComposeActorsTheme(darkTheme = true) {
        FavoriteActorsTabContent(
            navigateToSelectedActor = {},
            favoriteActors = emptyList(),
            removeFavoriteActor = {}
        )
    }
}