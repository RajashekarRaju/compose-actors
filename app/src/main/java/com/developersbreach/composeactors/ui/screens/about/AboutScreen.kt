package com.developersbreach.composeactors.ui.screens.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaImage
import com.developersbreach.designsystem.components.CaScaffold
import com.developersbreach.designsystem.components.CaSurface
import com.developersbreach.designsystem.components.CaText

@Composable
fun AboutScreen(
    navigateUp: () -> Unit,
) {
    CaSurface(
        color = MaterialTheme.colors.background,
        modifier = Modifier,
    ) {
        CaScaffold(
            modifier = Modifier,
            topBar = { AboutTopAppBar(navigateUp = navigateUp) },
        ) { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues),
            ) {
                CaImage(
                    painter = painterResource(id = R.drawable.ic_tmdb_logo),
                    contentDescription = stringResource(id = R.string.cd_tmdb_api_attribution_logo),
                    modifier = Modifier
                        .padding(80.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                )
                CaText(
                    text = stringResource(id = R.string.tmdb_api_attribution),
                    modifier = Modifier.padding(horizontal = 40.dp),
                    style = TextStyle(
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        brush = Brush.linearGradient(
                            listOf(
                                Color(0xFF90CEA1),
                                Color(0xFF3CBEC9),
                                Color(0xFF00B3E5),
                            ),
                        ),
                    ),
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun AboutScreenPreview() {
    ComposeActorsTheme {
        AboutScreen { }
    }
}