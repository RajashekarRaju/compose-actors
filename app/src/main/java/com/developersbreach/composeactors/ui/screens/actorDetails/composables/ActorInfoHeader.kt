package com.developersbreach.composeactors.ui.screens.actorDetails.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.ui.animations.borderRevealAnimation
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.composeactors.utils.calculateAge
import com.developersbreach.composeactors.utils.getPlaceOfBirth
import com.developersbreach.composeactors.utils.getPopularity
import com.developersbreach.designsystem.components.CaImage
import com.developersbreach.designsystem.components.CaTextH6
import com.developersbreach.designsystem.components.CaTextSubtitle2

/**
 * Row with 3 elements which shows actor details just below actor image.
 * 1. Age of actor
 * 2. Popularity
 * 3. Place of birth
 */
@Composable
internal fun ActorInfoHeader(
    actorData: PersonDetail?,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        item { AgeInfo(actorAge = actorData?.dateOfBirth) }
        item { PopularityInfo(popularity = actorData?.popularity) }
        item { CountryInfo(placeOfBirth = actorData?.placeOfBirth) }
    }
}

/**
 * First element in [ActorInfoHeader].
 * Shows calculated age of actor from [calculateAge]
 */
@Composable
private fun AgeInfo(
    actorAge: String?,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp),
    ) {
        Box(
            modifier = Modifier.borderRevealAnimation(),
        ) {
            CaTextH6(
                text = "${calculateAge(actorAge)}",
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.align(alignment = Alignment.Center),
            )
        }

        ActorInfoHeaderSubtitle(
            subtitle = stringResource(R.string.actor_age_subtitle),
        )
    }
}

/**
 * Second element in [ActorInfoHeader].
 * Fetches actors popularity from function [calculateAge].
 */
@Composable
private fun PopularityInfo(
    popularity: Double?,
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.borderRevealAnimation(),
        ) {
            CaTextH6(
                text = getPopularity(popularity),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(alignment = Alignment.Center),
            )
        }

        ActorInfoHeaderSubtitle(
            subtitle = stringResource(R.string.actor_popularity_subtitle),
        )
    }
}

/**
 * Third element in [ActorInfoHeader].
 * Fetches place of birth from function [calculateAge].
 */
@Composable
private fun CountryInfo(
    placeOfBirth: String?,
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.borderRevealAnimation(),
        ) {
            CaImage(
                painter = painterResource(id = R.drawable.ic_globe),
                contentDescription = stringResource(R.string.cd_place_of_birth_icon),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface),
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .size(32.dp),
            )
        }

        ActorInfoHeaderSubtitle(
            subtitle = "${getPlaceOfBirth(placeOfBirth)}",
        )
    }
}

@Composable
private fun ActorInfoHeaderSubtitle(
    subtitle: String,
) {
    CaTextSubtitle2(
        text = subtitle,
        modifier = Modifier.padding(vertical = 8.dp),
        color = MaterialTheme.colors.onBackground,
    )
}

@PreviewLightDark
@Composable
private fun ActorInfoHeaderPreview() {
    ComposeActorsTheme {
        ActorInfoHeader(
            actorData = PersonDetail(
                personId = 1,
                personName = "Kate WinsletKate Winslet",
                profilePath = "",
                biography = "Kate Elizabeth Winslet CBE born 5 October 1975 is an English actress. Known for her work in independent films, particularly period dramas, and for her portrayals of headstrong and complicated women, she has received numerous accolades, including an Academy Award, a Grammy Award, two Primetime Emmy Awards, three BAFTA Awards, and five Golden Globe Awards. Time magazine named Winslet one of the 100 most influential people in the world in 2009 and 2021. She was appointed Commander of the Order of the British Empire (CBE) in 2012.",
                dateOfBirth = "47",
                placeOfBirth = "UK",
                popularity = 52.0,
            ),
        )
    }
}