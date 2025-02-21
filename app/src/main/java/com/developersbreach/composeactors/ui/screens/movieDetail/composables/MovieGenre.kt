package com.developersbreach.composeactors.ui.screens.movieDetail.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.data.movie.model.Genre
import com.developersbreach.designsystem.components.CaCard

@Composable
fun MovieGenre(
    genres: List<Genre>?
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        genres?.let {
            items(it) { genre ->
                CaCard(
                    shape = RoundedCornerShape(24.dp),
                    backgroundColor = MaterialTheme.colors.surface,
                    modifier = Modifier,
                    content = {
                        Text(
                            text = genre.genreName,
                            color = MaterialTheme.colors.onSurface,
                            style = MaterialTheme.typography.subtitle2,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                )
            }
        }
    }
}