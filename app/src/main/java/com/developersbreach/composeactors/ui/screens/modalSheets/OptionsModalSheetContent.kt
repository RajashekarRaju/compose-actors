package com.developersbreach.composeactors.ui.screens.modalSheets

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.model.MenuItem
import com.developersbreach.composeactors.data.model.MovieDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OptionsModalSheetContent(
    modalSheetSheet: ModalBottomSheetState,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navigateToFavorite:() -> Unit,
    menuItemList: List<MenuItem>,
    selectedMovieDetail: MovieDetail?
    ) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 32.dp)
    ) {
        IconButton(
            modifier = Modifier.size(28.dp),
            onClick = {
                coroutineScope.launch {
                    modalSheetSheet.animateTo(
                        targetValue = ModalBottomSheetValue.Hidden,
                        anim = tween(durationMillis = 350)
                    )
                }
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                contentDescription = "",
                tint = MaterialTheme.colors.onBackground
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.baseline_developer_mode_24),
                contentDescription = "",
                modifier = Modifier.size(dimensionResource(id = R.dimen.bottom_app_bar_icon_size))
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            contentPadding = PaddingValues(start = 4.dp)
        ) {
            items(menuItemList.size) { index ->
                val menuItem = menuItemList[index]
                ItemOptionRow(
                    navigateToFavorite = navigateToFavorite,
                    selectedMovieDetail = selectedMovieDetail,
                    menuItem = menuItem,
                    index = index,
                )
            }
        }
    }
}

@Composable
private fun ItemOptionRow(
    navigateToFavorite: () -> Unit,
    selectedMovieDetail: MovieDetail?,
    menuItem: MenuItem,
    index: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                when (index) {
                    0 -> {
                        navigateToFavorite()
                    }
                }
            }
    ) {
        Icon(
            painter = painterResource(id = menuItem.getIconId()),
            contentDescription = "",
            tint = MaterialTheme.colors.onBackground.copy(alpha = 0.75f)
        )

        Spacer(modifier = Modifier.width(24.dp))

        Text(
            text = menuItem.getTitle(),
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.75f),
            style = MaterialTheme.typography.h5,
        )
    }
}
