package com.developersbreach.composeactors.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.utils.getCountryListWithCode

@Composable
fun RegionEditDialog(
    popupState: MutableState<Boolean>,
    onRegionSelect: (String, String) -> Unit
) {
    if (popupState.value) {
        val regions = getCountryListWithCode()
        Popup(alignment = Alignment.Center, onDismissRequest = { popupState.value = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(horizontal = 50.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
            ) {
                DropdownMenu(
                    regionList = regions,
                    modifier = Modifier,
                    onRegionSelect = onRegionSelect,
                    popupState = popupState
                )
            }
        }
    }
}

@Composable
fun DropdownMenu(
    regionList: List<String>,
    modifier: Modifier = Modifier,
    onRegionSelect: (String, String) -> Unit,
    popupState: MutableState<Boolean>
) {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colors.surface)
            .padding(horizontal = 16.dp)
    ) {

        Header(popupState)

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(regionList) { region ->
                val countryCodeAndName = region.split(",")
                DropdownMenuItem(
                    text = countryCodeAndName[1],
                    onClick = { onRegionSelect(countryCodeAndName[0], countryCodeAndName[1]) },
                    popupState = popupState
                )
            }
        }
    }
}

@Composable
fun Header(popupState: MutableState<Boolean>) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(8.dp)
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            stringResource(R.string.choose_region),
            style = MaterialTheme.typography.subtitle1
        )
        Image(
            imageVector = Icons.Rounded.Close,
            contentDescription = stringResource(R.string.close_pop_up_icon),
            modifier = Modifier.clickable {
                popupState.value = false
            }
        )
    }
}

@Composable
fun DropdownMenuItem(
    text: String,
    onClick: () -> Unit,
    popupState: MutableState<Boolean>
) {
    Box(
        modifier = Modifier
            .clickable {
                onClick()
                popupState.value = false
            }
            .background(MaterialTheme.colors.surface)
            .padding(horizontal = 24.dp)
            .padding(8.dp)
    ) {
        Text(text, style = MaterialTheme.typography.subtitle2)
    }
}

@Preview
@Composable
private fun RegionEditDialogPreview() {
    RegionEditDialog(
        popupState = remember { mutableStateOf(true) },
        onRegionSelect = { _: String, _: String -> }
    )
}
