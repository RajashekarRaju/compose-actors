package com.developersbreach.composeactors.ui.actors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme

@Composable
fun SearchBar() {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 4.dp, end = 16.dp)
            .height(56.dp)
            .background(
                color = MaterialTheme.colors.surface,
                RoundedCornerShape(8.dp)
            )
    ) {

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Icon",
            tint = Color.Gray,
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(1.5f)
        )

        Text(
            text = "Search contacts",
            color = Color.Gray,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .weight(8.5f)
                .padding(start = 16.dp)
        )
    }
}

@Preview(widthDp = 360, heightDp = 56)
@Composable
fun SearchBarPreview() {
    ComposeActorsTheme(darkTheme = true) {
        SearchBar()
    }
}
