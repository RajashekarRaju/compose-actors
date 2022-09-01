package com.developersbreach.composeactors.ui.screens.actorDetails.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.developersbreach.composeactors.data.model.ActorDetail

/**
 * Row with 3 elements which shows actor details just below actor image.
 * 1. Age of actor
 * 2. Popularity
 * 3. Place of birth
 */
@Composable
fun ActorInfoHeader(
    actorData: ActorDetail?
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