package com.developersbreach.composeactors.ui.modalSheet

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
 * TODO
 * Complete modal sheet content for showing actor details instead of navigating to
 * ActorDetails screen directly.
 */
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
        item {

            Spacer(modifier = Modifier.height(16.dp))

            Divider(
                color = MaterialTheme.colors.onBackground.copy(0.50f),
                thickness = 4.dp,
                modifier = Modifier
                    .clip(RoundedCornerShape(percent = 100))
                    .width(48.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            LoadNetworkImage(
                imageUrl = actor?.profileUrl,
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

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = actor?.actorName.toString(),
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            ActorInfoHeader(actorData = actor)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = actor?.biography.toString(),
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

            Spacer(
                modifier = Modifier
                    .height(24.dp)
            )
        }
    }
}



