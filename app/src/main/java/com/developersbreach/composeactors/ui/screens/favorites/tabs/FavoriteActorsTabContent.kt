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
import com.developersbreach.composeactors.data.datasource.fake.fakeFavoritePersonsList
import com.developersbreach.composeactors.data.person.model.FavoritePerson
import com.developersbreach.composeactors.ui.components.ImageBackgroundThemeGenerator
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.components.verticalGradientScrim
import com.developersbreach.composeactors.ui.screens.favorites.NoFavoritesFoundUI
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.composeactors.utils.getPlaceOfBirth

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritePersonsTabContent(
    navigateToSelectedPerson: (Int) -> Unit,
    favoritePeople: List<FavoritePerson>,
    removeFavoritePerson: (FavoritePerson) -> Unit,
) {
    if (favoritePeople.isEmpty()) {
        NoFavoritesFoundUI()
    }

    val listState = rememberPagerState(
        pageCount = { favoritePeople.size }
    )

    VerticalPager(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        pageSpacing = 24.dp,
        pageSize = PageSize.Fixed(512.dp),
        contentPadding = PaddingValues(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 48.dp),
    ) { currentPage ->
        ItemFavoritePerson(
            item = favoritePeople[currentPage],
            onClickPerson = navigateToSelectedPerson,
            removeFavoritePerson = removeFavoritePerson
        )
    }
}

@Composable
private fun ItemFavoritePerson(
    item: FavoritePerson,
    onClickPerson: (Int) -> Unit,
    removeFavoritePerson: (FavoritePerson) -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .clip(MaterialTheme.shapes.large)
    ) {
        LoadNetworkImage(
            imageUrl = item.profileUrl,
            contentDescription = "Actor Image",
            shape = RectangleShape,
            showAnimProgress = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(512.dp)
                .clickable {
                    onClickPerson(item.personId)
                }
        )

        ImageBackgroundThemeGenerator(
            imageUrl = item.profileUrl,
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
            imageUrl = item.profileUrl,
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
                        text = item.personName,
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Text(
                        text = "${getPlaceOfBirth(item.placeOfBirth)}",
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onPrimary,
                    )
                }

                IconButton(
                    onClick = { removeFavoritePerson(item) },
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
private fun FavoritePersonsTabContentPreviewLightPreview() {
    ComposeActorsTheme(darkTheme = false) {
        FavoritePersonsTabContent(
            navigateToSelectedPerson = {},
            favoritePeople = fakeFavoritePersonsList(),
            removeFavoritePerson = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF211a18)
@Composable
private fun FavoritePersonsTabContentPreviewDarkPreview() {
    ComposeActorsTheme(darkTheme = true) {
        FavoritePersonsTabContent(
            navigateToSelectedPerson = {},
            favoritePeople = fakeFavoritePersonsList(),
            removeFavoritePerson = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritePersonsTabContentNoFavoritesPreviewLightPreview() {
    ComposeActorsTheme(darkTheme = false) {
        FavoritePersonsTabContent(
            navigateToSelectedPerson = {},
            favoritePeople = emptyList(),
            removeFavoritePerson = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF211a18)
@Composable
private fun FavoriteActorsTabContentNoFavoritesPreviewDarkPreview() {
    ComposeActorsTheme(darkTheme = true) {
        FavoritePersonsTabContent(
            navigateToSelectedPerson = {},
            favoritePeople = emptyList(),
            removeFavoritePerson = {}
        )
    }
}