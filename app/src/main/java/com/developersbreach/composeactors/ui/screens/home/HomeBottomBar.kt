package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.model.BottomSheetType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeBottomBar(
    modalSheetSheet: ModalBottomSheetState,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    currentBottomSheetCallback: (BottomSheetType) -> Unit = {}
) {
    BottomAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp,
        contentPadding = paddingValues,
        modifier = Modifier
            .clip(RoundedCornerShape(topStartPercent = 25, topEndPercent = 25))
            .background(MaterialTheme.colors.surface)
            .navigationBarsPadding()
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        currentBottomSheetCallback(BottomSheetType.MoreMenu)
                        coroutineScope.launch {
                            modalSheetSheet.animateTo(
                                targetValue = ModalBottomSheetValue.Expanded,
                                anim = tween(durationMillis = 350)
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(28.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_keyboard_arrow_up_24),
                        contentDescription = "",
                        tint = MaterialTheme.colors.onBackground
                    )
                }

                Text(
                    text = stringResource(id = R.string.app_name),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PreviewHomeBottomBar() {
    HomeBottomBar(
        modalSheetSheet = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.HalfExpanded,
            animationSpec = tween(durationMillis = 500),
            skipHalfExpanded = true
        ),
        PaddingValues(0.dp),
        rememberCoroutineScope ())
}