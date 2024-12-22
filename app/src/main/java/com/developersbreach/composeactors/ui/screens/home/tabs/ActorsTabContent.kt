package com.developersbreach.composeactors.ui.screens.home.tabs

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.ui.components.AppDivider
import com.developersbreach.composeactors.ui.components.CategoryTitle
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.components.ShowProgressIndicator
import com.developersbreach.composeactors.ui.screens.actorDetails.ActorDetailsScreen
import com.developersbreach.composeactors.ui.screens.home.HomeUIState


@Composable
fun PersonsTabContent(
    homeUIState: HomeUIState,
    navigateToSelectedPerson: (Int) -> Unit,
    popularPersonsListState: LazyListState,
    trendingPersonsListState: LazyListState
) {
    ShowProgressIndicator(isLoadingData = homeUIState.isFetchingPersons)

    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            CategoryTitle(stringResource(R.string.category_actors_popular))
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            PersonsList(
                personsList = homeUIState.popularPersonList,
                navigateToSelectedPerson = navigateToSelectedPerson,
                personsListState = popularPersonsListState
            )
            AppDivider(verticalPadding = 32.dp)
            CategoryTitle(stringResource(R.string.category_actors_trending))
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            PersonsList(
                personsList = homeUIState.trendingPersonList,
                navigateToSelectedPerson = navigateToSelectedPerson,
                personsListState = trendingPersonsListState
            )
        }
    }
}

/**
 * @param personsList row list elements of [Person]
 */
@Composable
private fun PersonsList(
    personsList: List<Person>,
    navigateToSelectedPerson: (Int) -> Unit,
    personsListState: LazyListState
) {
    LazyRow(
        state = personsListState,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(
            items = personsList,
            key = { it.personId }
        ) { person ->
            ItemPerson(
                person = person,
                onClickPerson = navigateToSelectedPerson
            )
        }
    }
}

/**
 * @param onClickPerson navigate to person [ActorDetailsScreen] from user selected person.
 */
@Composable
private fun ItemPerson(
    person: Person,
    onClickPerson: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clip(shape = MaterialTheme.shapes.large)
            .clickable(onClick = { onClickPerson(person.personId) })
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(vertical = 12.dp))

            LoadNetworkImage(
                imageUrl = person.profileUrl,
                contentDescription = stringResource(R.string.cd_actor_image),
                shape = CircleShape,
                modifier = Modifier
                    .size(120.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onSurface,
                        shape = CircleShape
                    )
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = person.personName,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.padding(vertical = 12.dp))
        }
    }
}