import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.datasource.fake.fakePersonsList
import com.developersbreach.composeactors.ui.screens.search.ActorSearch
import com.developersbreach.composeactors.ui.screens.search.SearchScreenUI
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme

class SearchScreenScreenshotTest {
    @Preview(showBackground = true)
    @Composable
    private fun SearchScreenUILightPreview() {
        ComposeActorsTheme(darkTheme = false) {
            SearchScreenUI(
                navigateUp = {},
                navigateToSearchBySearchType = {},
                searchHint = stringResource(R.string.hint_search_query_actors),
                onSearchQueryChange = {},
                data = ActorSearch(
                    personList = fakePersonsList(),
                    isSearchingResults = false
                )
            )
        }
    }
}