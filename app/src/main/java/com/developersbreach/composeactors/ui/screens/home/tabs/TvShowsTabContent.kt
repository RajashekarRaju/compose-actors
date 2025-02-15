package com.developersbreach.composeactors.ui.screens.home.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.screens.home.HomeSheetUIState
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaImage

@Composable
fun TvShowsTabContent(
    homeSheetUIState: HomeSheetUIState
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CaImage(
                painter = painterResource(id = R.drawable.ic_tv_show),
                contentDescription = "",
                modifier = Modifier.size(240.dp)
            )
            Text(
                text = "Feature Coming Soon",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 40.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenUIPreview() {
    ComposeActorsTheme {
        TvShowsTabContent(
            homeSheetUIState = HomeSheetUIState()
        )
    }
}