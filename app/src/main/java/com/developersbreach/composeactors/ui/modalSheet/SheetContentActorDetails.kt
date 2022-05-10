package com.developersbreach.composeactors.ui.modalSheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.model.ActorDetail
import com.developersbreach.composeactors.ui.actorDetails.ActorInfoHeader
import com.developersbreach.composeactors.ui.components.LoadNetworkImage

/**
 * Complete modal sheet content for showing actor details instead of navigating to
 * ActorDetails screen directly.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SheetContentActorDetails(
    actor: ActorDetail?,
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
    ) {
        stickyHeader {
            // Extra parent column added because, the sticky header needs background for
            // movie overview paragraph to be visible.
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.background)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                SheetSeparator()
                Spacer(modifier = Modifier.height(24.dp))
                ActorProfileImage(actor?.profileUrl)
                Spacer(modifier = Modifier.height(16.dp))
                ActorNameText(actor?.actorName.toString())
                Spacer(modifier = Modifier.height(16.dp))
                ActorInfoHeader(actorData = actor)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
            ActorBiographyText(actor?.biography.toString())
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SheetSeparator() {
    Divider(
        color = MaterialTheme.colors.onBackground.copy(0.50f),
        thickness = 4.dp,
        modifier = Modifier
            .clip(RoundedCornerShape(percent = 100))
            .width(48.dp)
    )
}

@Composable
private fun ActorProfileImage(
    profileUrl: String?
) {
    LoadNetworkImage(
        imageUrl = profileUrl,
        contentDescription = stringResource(id = R.string.cd_actor_image),
        shape = CircleShape,
        modifier = Modifier
            .size(120.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onBackground,
                shape = CircleShape
            )
    )
}

@Composable
private fun ActorNameText(
    actorName: String
) {
    Text(
        text = actorName,
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(horizontal = 24.dp)
    )
}

@Composable
private fun ActorBiographyText(
    biography: String
) {
    Text(
        text = biography,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        style = TextStyle(
            lineHeight = 20.sp,
            color = MaterialTheme.colors.onBackground.copy(0.8f),
            textAlign = TextAlign.Justify,
            fontSize = 16.sp
        )
    )
}



