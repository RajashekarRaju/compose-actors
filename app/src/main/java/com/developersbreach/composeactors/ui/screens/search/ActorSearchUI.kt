package com.developersbreach.composeactors.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.data.datasource.fake.fakePersonsList
import com.developersbreach.composeactors.data.model.Person
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme

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
    Text(
        text = person.personName,
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.onBackground,
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

@Preview(showBackground = true, backgroundColor = 0xFF211a18)
@Composable
private fun PersonSearchUIDarkPreview() {
    ComposeActorsTheme(darkTheme = true) {
        PersonSearchUI(
            persons = fakePersonsList(),
            navigateToSelectedPerson = {},
            closeKeyboard = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PersonSearchUILightPreview() {
    ComposeActorsTheme(darkTheme = false) {
        PersonSearchUI(
            persons = fakePersonsList(),
            navigateToSelectedPerson = {},
            closeKeyboard = {}
        )
    }
}