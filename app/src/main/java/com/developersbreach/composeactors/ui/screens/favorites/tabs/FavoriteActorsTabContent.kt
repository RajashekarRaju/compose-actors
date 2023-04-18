package com.developersbreach.composeactors.ui.screens.favorites.tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.model.FavoriteActor
import com.developersbreach.composeactors.ui.animations.borderRevealAnimation
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.screens.favorites.NoFavoritesFoundUI
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.composeactors.utils.getPlaceOfBirth

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteActorsTabContent(
    getSelectedActorDetails: (Int) -> Unit,
    favoriteActors: List<FavoriteActor>,
    removeFavoriteActor: (FavoriteActor) -> Unit,
) {
    if (favoriteActors.isEmpty()) {
        NoFavoritesFoundUI()
    }

    val listState = rememberPagerState()

    VerticalPager(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        pageCount = favoriteActors.size
    ) { currentPage ->
        val actor = favoriteActors[currentPage]
        ItemFavoriteActor(
            actorItem = actor,
            getSelectedActorDetails = getSelectedActorDetails,
            removeFavoriteActor = removeFavoriteActor
        )
    }
}

@Composable
fun ItemFavoriteActor(
    actorItem: FavoriteActor,
    getSelectedActorDetails: (Int) -> Unit,
    removeFavoriteActor: (FavoriteActor) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LoadNetworkImage(
            imageUrl = actorItem.profileUrl,
            contentDescription = "Actor Image",
            shape = RectangleShape,
            showAnimProgress = false,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    getSelectedActorDetails(actorItem.actorId)
                }
        )
        Spacer(
            Modifier
                .align(Alignment.BottomStart)
                .fillMaxSize()
                .background(color = Color.Gray.copy(alpha = 0.3f))
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
        ) {

            Row(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = actorItem.actorName,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.weight(0.8f)
                )

                IconButton(
                    onClick = { removeFavoriteActor(actorItem) },
                    modifier = Modifier.weight(0.1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_favorite),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
            }

            CountryInfo(placeOfBirth = actorItem.placeOfBirth)

        }
    }
}

@Composable
private fun CountryInfo(
    placeOfBirth: String?
) {
    Row(
        modifier = Modifier.padding(start = 24.dp, bottom = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.borderRevealAnimation(
                size = 30.dp
            )
        ) {
            Image(
                painterResource(id = R.drawable.ic_globe),
                contentDescription = stringResource(R.string.cd_place_of_birth_icon),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface),
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .size(16.dp)
            )
        }

        ActorInfoHeaderSubtitle(
            subtitle = "${getPlaceOfBirth(placeOfBirth)}"
        )
    }
}

@Composable
private fun ActorInfoHeaderSubtitle(
    subtitle: String
) {
    Text(
        text = subtitle,
        style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        color = MaterialTheme.colors.onPrimary,
    )
}

@Preview
@Composable
private fun FavoriteActorsTabContentPreview() {
    ComposeActorsTheme {
        FavoriteActorsTabContent(
            getSelectedActorDetails = {},
            favoriteActors = emptyList()
        ) {}
    }
}