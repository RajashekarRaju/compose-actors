# Compose Actors :dancer:

![AppBanner](https://github.com/RajashekarRaju/compose-actors/blob/dev/assets/banner.png)

## :dango: App Overview

Android app built with `Jetpack Compose` shows actors information fetched from Tmdb Api.
You may install and try to understand the code better, but make sure you provide your own Tmdb api
key for data to show up in directory `/utils/ApiKey.kt`.

| Screen | Preview |
| :----- | :------:|
|  **Home Screen** _(Default destination)_<br><br> • Shows category list of actors in row of type _popular & trending_.<br> • Has it's own ViewModel to manage it's ui state.<br> • Custom TopAppBar container with search box.<br> • Navigates to Search screen clicking search box.<br> • Navigates to Detail screen with castId clicking any cast item.<br> • If user is offline snackbar message is shown.<br> • CircularProgressIndicator will be shown untill data is fetched.<br> • Image fetching with Coil, manages state error/placeholder. | <img src="assets/home_dark_gif.gif" alt="Home screen preview" width="180" /> |
|        |                                                                              |
| **Search Screen**<br><br> • Shows list of actors based on user submitted query.<br> • Animatable shapes infinitely repeatable.<br> • Has it's own ViewModel to manage it's ui state.<br> • TextField contained in TopAppBar completely transparent.<br> • Navigates to Detail screen with castId clicking any cast item.<br> • Screen and animation state changes on search began.<br> • Handles query & value changes correctly to fetch results.<br> • Draw Arc/Line on canvas & animate to shape shift like search icon.<br> • Different colors for animatables for both light/dark theme. | <img src="assets/search_dark_gif.gif" alt="Search screen preview" width="180" /> |
|        |                                                                                  |
| **Detail Screen**<br><br> • Shows user selected actor from other screens.<br> • Has it's own ViewModel to manage it's ui state.<br> • Reveal effect animation added to few composables.<br> • CircularProgressIndicator will be shown untill data is fetched.<br> • Image fetching with Coil, manages state error/placeholder.<br> • Background image with gradient foreground effect.<br> • Draws dynamic color behind system bars. | <img src="assets/detail_dark_gif.gif" alt="Detail screen preview" width="180" /> |

## :mag: Search Animation

```kotlin
// Simple progressive circle looking animation
val animateCircle = remember { Animatable(0f) }.apply {
    AnimateShapeInfinitely(this)
}

@Composable
fun AnimateShapeInfinitely(
    // shape which will be animated infinitely.
    animateShape: Animatable<Float, AnimationVector1D>,
    // final float state to be animated.
    targetValue: Float = 1f,
    // duration took for animating once.
    durationMillis: Int = 1000
) {
    LaunchedEffect(animateShape) {
        animateShape.animateTo(
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis, LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }
}
```

Although I couldn't fully achieve the desired result as I imagined, I've settled for this current
state for now.

<img src="assets/search_anim.gif" alt="Offline Dark" />

Calling the function once will draw a circle, in my example I have drawn it thrice with differnt colors, radius and scales.

```kotlin
DrawCircleOnCanvas(
    scale = scaleInfiniteTransition(targetValue = 2f, durationMillis = 600),
    color = circleColor,
    radiusRatio = 4f
)
```

I have kept all initial states of 3 circles to 0f to make end result much smoother.<br>
Random or uneven gaps between initial/target/durationMillis will make end animation look more abrupt and aggressively pushing it's bounds.

```kotlin
@Composable
private fun scaleInfiniteTransition(
    initialValue: Float = 0f,
    targetValue: Float,
    durationMillis: Int,
): Float {
    val infiniteTransition = rememberInfiniteTransition()
    val scale: Float by infiniteTransition.animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    return scale
}
```

```kotlin
@Composable
fun DrawCircleOnCanvas(
    scale: Float,
    color: Color,
    radiusRatio: Float
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawCircle(
            color = color,
            center = Offset(
                x = canvasWidth / 2,
                y = canvasHeight / 2
            ),
            radius = size.minDimension / radiusRatio,
        )
    }
}
```

## :mobile_phone_off: Offline state

| Dark | Light |
| :--: | :---: |
| <img src="assets/img_offline_dark.jpg" alt="Offline Dark" width="200" /> | <img src="assets/img_offline_light.jpg" alt="Offline Light" width="200" /> |

Show a Snackbar message with `SnackbarHostState`.

```kotlin
if (!isOnline) {
    LaunchedEffect(scope) {
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message = context.getString(R.string.offline_snackbar_message),
                duration = SnackbarDuration.Indefinite
            )
        }
    }
}
```

## :tokyo_tower: Architecture

| Network |  | Repository |  | ViewModels |  | Screens |
| :-:     | :-:     | :-:     | :-:    | :-:     | :-:     | :-:     |
| `Data`<br> :gift::gift:<br> :gift::gift: | **--->** | `Source`<br> :gift: | **--->**<br>`Suspend` | `Coroutines`<br> :curly_loop::curly_loop: | **--->**<br>`State` | `Composables`<br>:iphone::iphone:<br>:iphone::iphone: |

### ViewModels

All screens have their own ViewModels for managing the ui state.

```kotlin
class ActorsViewModel(
    application: Application,
    private val repository: AppRepository
) : AndroidViewModel(application) {

    // Holds the state for values in ActorsViewState
    var uiState by mutableStateOf(ActorsViewState())
        private set

    init {
        // Update the values in uiState from all data sources.
        viewModelScope.launch {
            uiState = ActorsViewState(isFetchingActors = true)
            val popularActorsList = repository.getPopularActorsData()
            val trendingActorsList = repository.getTrendingActorsData()
            uiState = ActorsViewState(
                popularActorList = popularActorsList,
                trendingActorList = trendingActorsList,
                isFetchingActors = false
            )
        }
    }
}
```

Model for UI state of the screen.

```kotlin
data class ActorsViewState(
    var popularActorList: List<Actor> = emptyList(),
    var trendingActorList: List<Actor> = emptyList(),
    val isFetchingActors: Boolean = false,
)
```

ViewModel used in a screen-level composable.

```kotlin
@Composable
fun ActorsScreen(
    viewModel: ActorsViewModel
) {
    val uiState = viewModel.uiState
    Box {
        ScreenContent(uiState.popularActorList)
    }
}
```

### Repository

All ViewModels have access to repository which has single instance.

```kotlin
class AppRepository {

    private val networkService by lazy { NetworkService() }

    suspend fun getPopularActorsData(): List<Actor> {
        val listData: List<Actor>
        withContext(Dispatchers.IO) {
            listData = networkService.getPopularActors()
        }
        return listData
    }
}
```

Instantiated repository will be passed to all ViewModels.

```kotlin
val repository = (application as ComposeActorsApp).repository

NavHost(
    navController = navController,
    startDestination = startDestination
) {
    composable(
        "Destination Route"
    ) {
        ActorsScreen(
            viewModel = viewModel(
                factory = ActorsViewModel.provideFactory(
                    application, repository
                )
            )
        )
    }
}
```

## :hammer: Structure

| :file_folder: data | :file_folder: navigation | :file_folder: repository | :file_folder: root |
| :-|:-|:-|:- |
| :page_facing_up: ActorsFetcher.kt<br> :page_facing_up: JsonRemoteData.kt<br> :page_facing_up: Urls.kt | :page_facing_up: AppActions.kt<br> :page_facing_up: AppDestinations.kt<br> :page_facing_up: AppNavigation.kt | :page_facing_up: AppRepository.kt | :page_facing_up: MainActivity.kt<br> :page_facing_up: Application.kt |
| :file_folder: ui | :file_folder: utils | :file_folder: model |
| :file_folder: actors<br> :file_folder: details<br> :file_folder: search<br> :file_folder: components<br> :file_folder: theme | :page_facing_up: InfiniteFlowingThings.kt<br> :page_facing_up: RevealEffect.kt<br> :page_facing_up: Utilities.kt<br> :page_facing_up: DynamicThemeGenerator.kt<br> :page_facing_up: NetworkManager.kt<br> :page_facing_up: NetworkQueryUtils.kt | :page_facing_up: Actor.kt<br> :page_facing_up: ActorDetail.kt<br> :page_facing_up: Movie.kt |

### :file_folder: Packages in ui

| :file_folder: actors | :file_folder: details | :file_folder: search | :file_folder: components | :file_folder: theme |
| :-|:-|:-|:-|:- |
| :page_facing_up: ActorsScreen.kt<br> :page_facing_up: ActorsViewModel.kt<br> | :page_facing_up: DetailsScreen.kt<br> :page_facing_up: DetailsViewModel.kt | :page_facing_up: SearchScreen.kt<br> :page_facing_up: SearchViewModel.kt<br> :page_facing_up: AnimatedSearch.kt | :page_facing_up: AppBars.kt<br> :page_facing_up: Components.kt<br> :page_facing_up: NetworkImage.kt<br> :page_facing_up: Progress.kt | :page_facing_up: Color.kt<br> :page_facing_up: Shape.kt<br> :page_facing_up: Theme.kt<br> :page_facing_up: Type.kt |

## :cyclone: Image loading with Coil

Reusable composable used in all screens to load image from an Url.

```kotlin
@Composable
fun LoadNetworkImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier,
    shape: Shape
) {
    Image(
        painter = rememberImagePainter(
            data = imageUrl,
            builder = {
                placeholder(R.drawable.animated_progress)
                error(R.drawable.ic_image_not_available)
            }),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(shape)
            .background(color = MaterialTheme.colors.surface)
    )
}
```

Then we will just call the composable anywhere in app screens.

```kotlin
LoadNetworkImage(
    imageUrl = "https://image_url",
    contentDescription = stringResource(R.string.cd_movie_poster),
    modifier = Modifier.size(100.dp, 150.dp),
    shape = MaterialTheme.shapes.medium,
)
```

## :pencil: Blog

![AppPreview](https://github.com/RajashekarRaju/compose-actors/blob/dev/assets/main.gif)

## :bulb: Motivation and Context

`Jetpack Compose` is Android’s modern toolkit for building native UI. It enables you to quickly
bring your app to life with less code, powerful tools, and intuitive Kotlin APIs.

Understanding to implement own `Theme` `Shape` `Typography` `Color` has became bit easier by
referring to lot of official jetpack compose samples which are available in GitHub.

Best of all we got to do this in `Kotlin` way. Excited and long way to go from here.

## :art: App Theme

### :rainbow: Material Design 3.
Followed theming and color practices from Material Theme Builder Web Tool.
Learn more [here](https://material-foundation.github.io/material-theme-builder/)

### Color.kt

```kotlin
// Light theme colors
val light_primary = Color(0xFFaa370c)
val light_onPrimary = Color(0xFFffffff)
val light_background = Color(0xFFFFFAF9)
val light_onBackground = Color(0xFF211a18)
val light_surface = Color(0xFFFFE6DB)
val light_onSurface = Color(0xFF211a18)

// Dark theme colors
val dark_primary = Color(0xFFffb59c)
val dark_onPrimary = Color(0xFF5f1600)
val dark_background = Color(0xFF211a18)
val dark_onBackground = Color(0xFFede0dc)
val dark_surface = Color(0xFF302522)
val dark_onSurface = Color(0xFFede0dc)
```

### Theme.kt

```kotlin
val LightColorPalette = lightColors(
    primary = light_primary,
    onPrimary = light_onPrimary,
    background = light_background,
    onBackground = light_onBackground,
    surface = light_surface,
    onSurface = light_onSurface,
)

val DarkColorPalette = darkColors(
    primary = dark_primary,
    onPrimary = dark_onPrimary,
    background = dark_background,
    onBackground = dark_onBackground,
    surface = dark_surface,
    onSurface = dark_onSurface
)
```

### :white_circle::black_circle: Light/Dark theme screenshots

| Home | Search | Detail |
| :--: | :----: | :----: |
| <img src="assets/img_home_dark.png" alt="Home Dark" width="200" /> <br> <img src="assets/img_home_light.png" alt="Home Light" width="200" /> | <img src="assets/img_search_state_dark.png" alt="Search Dark" width="200" /> <br> <img src="assets/img_search_state_light.png" alt="Search Light" width="200" /> | <img src="assets/img_detail_adele_dark.png" alt="Detail Dark" width="200" /> <br> <img src="assets/img_detail_adele_light.png" alt="Detail Light" width="200" /> |

## :trophy: Credits

### :rocket: JetCaster

Check the official [JetCaster](https://github.com/android/compose-samples/tree/main/Jetcaster) example from Android Team, I have used their code to generate Swatch with Palette Api in my Detail screen.

### :key: Tmdb Api

Images and all information in app belongs to and taken from [Tmdb Api](https://developers.themoviedb.org/3).
I do not own any of it and only made use of it for this app demonstration purpose.

Obtain your own Tmdb Api Key
from [here](https://www.themoviedb.org/settings/api)

## License

```
Copyright 2021 Rajasekhar K E

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
