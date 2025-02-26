package com.developersbreach.composeactors.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.data.datasource.fake.fakePersonsList
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaTextH6

@Composable
fun PersonSearchUI(
    persons: List<Person>,
    navigateToSelectedPerson: (Int) -> Unit,
    closeKeyboard: () -> Unit?
) {
    LazyColumn(
        // This padding helps avoid content going behind the navigation bars.
        modifier = Modifier.padding(bottom = 48.dp)
    ) {
        items(persons) {
            ItemSearchPerson(
                person = it,
                onClickPerson = navigateToSelectedPerson,
                closeKeyboard = closeKeyboard
            )
        }
    }
}

@Composable
private fun ItemSearchPerson(
    person: Person,
    onClickPerson: (Int) -> Unit,
    closeKeyboard: () -> Unit?
) {
    CaTextH6(
        text = person.personName,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                closeKeyboard()
                onClickPerson(person.personId)
            }
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .wrapContentWidth(Alignment.Start)
    )
}

@PreviewLightDark
@Composable
private fun PersonSearchUIPreview() {
    ComposeActorsTheme {
        PersonSearchUI(
            persons = fakePersonsList(),
            navigateToSelectedPerson = {},
            closeKeyboard = {}
        )
    }
}