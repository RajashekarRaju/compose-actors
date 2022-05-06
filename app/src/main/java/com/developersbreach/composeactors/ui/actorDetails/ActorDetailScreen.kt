package com.developersbreach.composeactors.ui.actorDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.model.ActorDetail
import com.developersbreach.composeactors.model.Movie
import com.developersbreach.composeactors.ui.components.CategoryTitle
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.components.ShowProgressIndicator
import com.developersbreach.composeactors.ui.home.HomeScreen
import com.developersbreach.composeactors.ui.modalSheet.SheetContentMovieDetails
import com.developersbreach.composeactors.ui.search.SearchScreen
import com.developersbreach.composeactors.utils.*
import kotlinx.coroutines.launch


/**
 * Shows details of user selected actor.
 *
 * @param viewModel to manage ui state of [DetailScreen]
 * @param navigateUp navigates user to previous screen.
 *
 * This destination can be accessed from [HomeScreen] & [SearchScreen].
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(
    selectedMovie: (Int) -> Unit,
    navigateUp: () -> Unit,
    viewModel: ActorDetailsViewModel
) {
    val uiState = viewModel.uiState
    val sheetUiState = viewModel.sheetUiState
    val actorProfile = "${uiState.actorData?.profileUrl}"

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetContent = {
            SheetContentMovieDetails(
                movie = sheetUiState.selectedMovieDetails,
                selectedMovie = selectedMovie
            )
        },
    ) {
        ActorDynamicTheme(
            podcastImageUrl = actorProfile
        ) {
            Box {
                // Draws gradient from image and overlays on it.
                ActorBackgroundWithGradientForeground(imageUrl = actorProfile)
                // Main details content
                ContentDetail(navigateUp, viewModel, modalSheetState)
                // Progress bar
                ShowProgressIndicator(isLoadingData = uiState.isFetchingDetails)
            }
        }
    }
}

/**
 * This image takes up whole space in screen as a background with reduced opacity.
 * On foreground draw vertical gradient so that top elements will be visible.
 *
 * @param imageUrl url to load image with coil.
 */
@Composable
private fun ActorBackgroundWithGradientForeground(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Box {
        LoadNetworkImage(
            imageUrl = imageUrl,
            contentDescription = stringResource(R.string.cd_actor_banner),
            shape = RectangleShape,
            showAnimProgress = false,
            modifier = modifier
                .fillMaxSize()
                .alpha(0.5f)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalGradientScrim(
                    color = MaterialTheme.colors.primary.copy(alpha = 0.50f),
                    startYPercentage = 1f,
                    endYPercentage = 0f
                )
        )
    }
}

// Main content for this screen
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ContentDetail(
    navigateUp: () -> Unit,
    viewModel: ActorDetailsViewModel,
    modalSheetState: ModalBottomSheetState,
) {
    val actorData = viewModel.uiState.actorData

    Column {

        DetailAppBar(
            navigateUp = navigateUp,
            title = "${actorData?.actorName}"
        )

        /** Sticky actor details content */
        Spacer(modifier = Modifier.padding(top = 16.dp))

        ActorRoundProfile("${actorData?.profileUrl}")

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        /** Scrollable actor details content */
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { ActorInfoHeader(actorData) }
            item { ActorCastedMovies(viewModel, modalSheetState) }
            item { ActorBiography(actorData?.biography) }
        }
    }
}

/**
 * Load circular network image of actor at the top of screen.
 */
@Composable
private fun ActorRoundProfile(
    profileUrl: String,
    size: Dp = 120.dp
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LoadNetworkImage(
            imageUrl = profileUrl,
            contentDescription = stringResource(id = R.string.cd_actor_image),
            shape = CircleShape,
            modifier = Modifier
                .size(size)
                .border(
                    width = 4.dp,
                    color = MaterialTheme.colors.surface,
                    shape = CircleShape
                )
        )
    }
}

/**
 * Row with 3 elements which shows actor details just below actor image.
 * 1. Age of actor
 * 2. Popularity
 * 3. Place of birth
 */
@Composable
private fun ActorInfoHeader(
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

/**
 * Cast icon and title on top and list below.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ActorCastedMovies(
    viewModel: ActorDetailsViewModel,
    modalSheetState: ModalBottomSheetState
) {
    val cast: List<Movie> = viewModel.uiState.castList
    val coroutineScope = rememberCoroutineScope()

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_movies_cast),
            contentDescription = stringResource(R.string.cd_cast_icon),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface),
            alpha = 0.5f,
            modifier = Modifier
                .padding(start = 12.dp)
                .size(36.dp)
        )

        CategoryTitle(
            title = stringResource(R.string.cast_movie_title)
        )
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            items = cast,
            key = { it.movieId }
        ) { movie ->
            LoadNetworkImage(
                imageUrl = movie.posterPathUrl,
                contentDescription = stringResource(R.string.cd_movie_poster),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .size(100.dp, 150.dp)
                    .clickable {
                        viewModel.getSelectedMovieDetails(movie.movieId)
                        coroutineScope.launch {
                            modalSheetState.show()
                        }
                    },
            )
        }
    }
}

/**
 * @param biography shows paragraph with gradient background drawn from image.
 * Biography icon with title on top and paragraph below.
 */
@Composable
private fun ActorBiography(
    biography: String?
) {
    Column(
        modifier = Modifier
            .verticalGradientScrim(
                color = MaterialTheme.colors.surface.copy(alpha = 0.75f),
                startYPercentage = 0f,
                endYPercentage = 1f
            )
            .padding(
                bottom = 56.dp,
                start = 16.dp,
                end = 16.dp
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_biography),
                contentDescription = stringResource(id = R.string.cd_biography_icon),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface),
                alpha = 0.5f,
                modifier = Modifier.size(36.dp),
            )

            CategoryTitle(
                title = stringResource(R.string.cast_biography_title)
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        // Make use of style to modify line height for the paragraph.
        if (biography != null) {
            Text(
                text = biography,
                style = TextStyle(
                    lineHeight = 20.sp,
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Justify
                )
            )
        }
    }
}

/**
 * First element in [ActorInfoHeader].
 * Shows calculated age of actor from [calculateAge]
 */
@Composable
private fun AgeInfo(
    actorAge: String?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Box(
            modifier = Modifier.borderRevealAnimation()
        ) {
            Text(
                text = "${calculateAge(actorAge)}",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }

        ActorInfoHeaderSubtitle(
            subtitle = stringResource(R.string.actor_age_subtitle)
        )
    }
}

/**
 * Second element in [ActorInfoHeader].
 * Fetches place of birth from function [calculateAge].
 */
@Composable
private fun CountryInfo(
    placeOfBirth: String?
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.borderRevealAnimation()
        ) {
            Image(
                painterResource(id = R.drawable.ic_globe),
                contentDescription = stringResource(R.string.cd_place_of_birth_icon),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface),
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .size(32.dp)
            )
        }

        ActorInfoHeaderSubtitle(
            subtitle = "${getPlaceOfBirth(placeOfBirth)}"
        )
    }
}

/**
 * Third element in [ActorInfoHeader].
 * Fetches actors popularity from function [calculateAge].
 */
@Composable
private fun PopularityInfo(
    popularity: Double?
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.borderRevealAnimation()
        ) {
            Text(
                text = getPopularity(popularity),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(alignment = Alignment.Center)
            )
        }

        ActorInfoHeaderSubtitle(
            subtitle = stringResource(R.string.actor_popularity_subtitle)
        )
    }
}

@Composable
private fun ActorInfoHeaderSubtitle(
    subtitle: String
) {
    Text(
        text = subtitle,
        style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(vertical = 8.dp),
        color = MaterialTheme.colors.onBackground
    )
}