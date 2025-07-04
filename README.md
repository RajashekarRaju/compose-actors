# Compose Actors :dancer:

![AppBanner](/assets/banner.png)

## Download the APK
Access the latest APK for Compose Actors from the link below.

[![Get APK](https://img.shields.io/badge/Get%20APK-maroon?style=for-the-badge&logo=android&logoColor=white)](https://github.com/RajashekarRaju/compose-actors/releases/download/v0.4.0/app-release.apk)

## Current Roadmap v0.5.0

- [x] Prepare development and production environments for initial release
- [ ] Improve error handling and user feedback messages.
- [ ] Complete tests for app navigation for all destinations.
- [ ] Create/Review privacy policy, usage rights, attributions for store publishing.
- [ ] Create publicising assets, banners for store.
- [ ] Create production environment for existing services and integrations used in app.
- [ ] Complete remaining screenshot tests
- [ ] Cover offline handling, network requiring flows, edge cases.
- [ ] Ktlint tests for validating hardcode strings and resources, enforce localization.

## Roadmap v0.4.0
<details>

- [x] Move material design components and dependencies to separate design-system module
- [x] Extend Konsist tests for all design system component usages.
- [x] Introduce common preview annotations for composables.
- [x] Integrate Compose preview screenshot testing, extend tests for all screens.
- [x] Configure Ktlint - integration for code formatting and linting automation.
- [x] Configure ktor mock engine and client provider for API usage and tests
- [x] Migrate app navigation graph to type‑safe DSL to simplify navigation structure
- [x] Integrate amazon Amplify service for authentication support.
- [x] Allow people to create accounts and self sign-up, implement sign-up screen.
- [x] Allow people fo user app as a Guest, skip login + guest flow.
- [x] Allow authenticated users to mange authentication with profile screen.
- [x] Integrate with compose actors backend to manage authenticated users data/watchlist.
- [x] Target newest sdk version, Upgrade to kotlin 2.1.21, latest compose-bom, fix deprecations.
- [x] Authenticated users can create movie watchlist to compose actors backend api.
- [x] Authenticated users can create people watchlist to compose actors backend api.

</details>

## Roadmap v0.6.0

- [ ] Add new feature seasons or series information to app.
- [ ] Include seasons in home tabs navigation.
- [ ] Allow adding seasons to favorites.
- [ ] Move favorites section from home tab to new place (Restructure all screen flows).
- [ ] Collapsable TopBars, BottomBars, Scroll effects, new animations.
- [ ] Migrate to Navigation3
- [ ] Migrate from Material2 -> Material3
- [ ] Allow users to tap movie’s streaming option & open the corresponding app (e.g - Netflix).
- [ ] Enable in-app playback of movie trailers so users can watch previews”
- [ ] Generic AppBar centered
- [ ] CASurface

## V3 Previews

### Home Tabs

| Actors | Movies | Favorites |
| :----: | :----: | :-------: |
| <img src="/assets/img_home_tab_actors.png" width="200"/> | <img src="/assets/img_home_tab_movies.png" width="200" /> | <img src="/assets/img_movies_tab_favorites.png" width="200" /> |

### Modal bottom sheets

| Actor | Movie |
| :---: | :---: |
| <img src="/assets/img_actor_overview_sheet.png" width="200"/> | <img src="/assets/img_movie_overview_sheet.png" width="200" /> |

### Movie details & Add to favorites

| Add to favorites | Favorites | Details |
| :--------------: | :-------: | :-----: |
| <img src="/assets/img_movie_favorites_sheet_new.png" width="200"/> | <img src="/assets/img_movies_tab_favorites.png" width="200" /> | <img src="/assets/img_movie_detail_screen_2.png" width="200" /> |

### Voice search actors

| Search capabilities |
| :---: |
| <img src="/assets/img_search_with_voice.png" width="200"/> |

*Inspired from*
| [JetCaster](https://github.com/android/compose-samples/tree/main/Jetcaster) | [JetNews](https://github.com/android/compose-samples/tree/main/JetNews) | [JetSnack](https://github.com/android/compose-samples/tree/main/Jetsnack) |
| :-: | :-: | :-: |
> [More compose content](https://developersbreach.com/compose/)

## :tokyo_tower: Architecture

> ### Follows new architecture guide updated on [**14 December 2021**](https://android-developers.googleblog.com/2021/12/rebuilding-our-guide-to-app-architecture.html) from revamped guide to app architecture.

#### :musical_keyboard: Layer of this app.

| Network |  | Repository |  | ViewModels |  | Screens |
| :-:     | :-:     | :-:     | :-:    | :-:     | :-:     | :-:     |
| `Data`<br> :gift::gift:<br> :gift::gift: | **--->** | `Source`<br> :gift: | **--->**<br>`Suspend` | `Coroutines`<br> :curly_loop::curly_loop: | **--->**<br>`State` | `Composables`<br>:iphone::iphone:<br>:iphone::iphone: |

![ArchitectureLayer](/assets/architecture_layer.png)

## :dango: App Overview  [Compose Blog](https://developersbreach.com/compose/)

Android app built with `Jetpack Compose` shows actors information fetched from Tmdb Api.
Supports users to manage your watchlist with own [Backend](https://github.com/RajashekarRaju/compose-actors-backend).
You may install and try to understand the code better, but make sure you provide your own Tmdb api
key for data to show up in directory `/utils/ApiKey.kt`.

<!-- Screen Description and Preview Section
### Release - v0.1.0

| Screen | Preview |
| :----- | :------:|
|  **Home Screen** _(Default destination)_<br><br> • Shows category list of actors in row of type _popular & trending_.<br> • Has it's own ViewModel to manage it's ui state.<br> • Custom TopAppBar container with search box.<br> • Navigates to Search screen clicking search box.<br> • Navigates to Detail screen with castId clicking any cast item.<br> • If user is offline snackbar message is shown.<br> • CircularProgressIndicator will be shown untill data is fetched.<br> • Image fetching with Coil, manages state error/placeholder. | <img src="assets/home_dark_gif.gif" alt="Home screen preview" width="180" /> |
|        |                                                                              |
| **Search Screen**<br><br> • Shows list of actors based on user submitted query.<br> • Animatable shapes infinitely repeatable.<br> • Has it's own ViewModel to manage it's ui state.<br> • TextField contained in TopAppBar completely transparent.<br> • Navigates to Detail screen with castId clicking any cast item.<br> • Screen and animation state changes on search began.<br> • Handles query & value changes correctly to fetch results.<br> • Draw Arc/Line on canvas & animate to shape shift like search icon.<br> • Different colors for animatables for both light/dark theme. | <img src="assets/search_dark_gif.gif" alt="Search screen preview" width="180" /> |
|        |                                                                                  |
| **Detail Screen**<br><br> • Shows user selected actor from other screens.<br> • Has it's own ViewModel to manage it's ui state.<br> • Reveal effect animation added to few composables.<br> • CircularProgressIndicator will be shown untill data is fetched.<br> • Image fetching with Coil, manages state error/placeholder.<br> • Background image with gradient foreground effect.<br> • Draws dynamic color behind system bars. | <img src="assets/detail_dark_gif.gif" alt="Detail screen preview" width="180" /> |
-->

## :mag: Search Animation

<img src="assets/search_anim.gif" alt="Offline Dark" />

## :mobile_phone_off: No TMDB_API_KEY provided state

| Dark | Light |
| :--: | :---: |
| <img src="assets/offline_dark.gif" alt="Offline Dark" width="200" /> | <img src="assets/offline_light.gif" alt="Offline Light" width="200" /> |

## :hammer: Structure
<!--
| :file_folder: data | :file_folder: navigation | :file_folder: repository | :file_folder: root |
| :-|:-|:-|:- |
| :page_facing_up: NetworkDataSource.kt<br> :page_facing_up: JsonRemoteData.kt<br> :page_facing_up: Urls.kt | :page_facing_up: NavigationActions.kt<br> :page_facing_up: AppDestinations.kt<br> :page_facing_up: AppNavigation.kt | :page_facing_up: AppRepository.kt | :page_facing_up: MainActivity.kt<br> :page_facing_up: Application.kt |
| :file_folder: ui | :file_folder: utils | :file_folder: model |
| :file_folder: home<br> :file_folder: details<br> :file_folder: search<br> :file_folder: components<br> :file_folder: theme | :page_facing_up: InfiniteFlowingThings.kt<br> :page_facing_up: RevealEffect.kt<br> :page_facing_up: Utilities.kt<br> :page_facing_up: DynamicThemeGenerator.kt<br> :page_facing_up: NetworkManager.kt<br> :page_facing_up: NetworkQueryUtils.kt | :page_facing_up: Actor.kt<br> :page_facing_up: ActorDetail.kt<br> :page_facing_up: Movie.kt |

### :file_folder: Packages in ui

| :file_folder: home | :file_folder: details | :file_folder: search | :file_folder: components | :file_folder: theme |
| :-|:-|:-|:-|:- |
| :page_facing_up: HomeScreen.kt<br> :page_facing_up: HomeViewModel.kt<br> | :page_facing_up: DetailsScreen.kt<br> :page_facing_up: DetailsViewModel.kt | :page_facing_up: SearchScreen.kt<br> :page_facing_up: SearchViewModel.kt<br> :page_facing_up: AnimatedSearch.kt | :page_facing_up: AppBars.kt<br> :page_facing_up: Components.kt<br> :page_facing_up: NetworkImage.kt<br> :page_facing_up: Progress.kt | :page_facing_up: Color.kt<br> :page_facing_up: Shape.kt<br> :page_facing_up: Theme.kt<br> :page_facing_up: Type.kt |
-->

```bash
root
├── core
│   ├── cache
│   ├── database
│   └── network
├── data
│   ├── datasource
│   │   ├── database
│   │   └── mocks
│   ├── feature_1
│   │   ├── model
│   │   ├── paging
│   │   ├── remote
│   │   └── repository
│   ├── feature_n
│   │   ├── model
│   │   ├── remote
│   │   └── repository
├── di
│   ├── module_1 
│   └── module_n 
├── ui
│   ├── animation
│   ├── navigation
│   ├── screen
│   │   ├── feature_1
│   │   └── feature_n
│   ├── components
│   └── theme
├── utils
├── domain
|   ├── feature_1
|   └── feature_n
├── MainActivity.kt
└── Application.kt
```

## :art: App Theme

### :rainbow: Material Design 2.
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
| <img src="assets/img_home_dark.png" alt="Home Dark" width="200" /> <br> <img src="assets/img_home_light.png" alt="Home Light" width="200" /> | <img src="assets/img_search_state_dark.png" alt="Search Dark" width="200" /> <br> <img src="assets/img_search_state_light.png" alt="Search Light" width="200" /> | <img src="assets/img_detail_dark.png" alt="Detail Dark" width="200" /> <br> <img src="assets/img_detail_light.png" alt="Detail Light" width="200" /> |

## :pencil: Blog

| <img src="/assets/reveal-effect-ani-blog-preview-image.png" alt="Article banner" width="600" /> |
| :-: |
| **Reveal effect animations in compose jetpack android** |
| [*Read article*](https://developersbreach.com/reveal-effect-animations-compose-android/) |

| <img src="/assets/banner.png" alt="Article banner" width="600" /> |
| :-: |
| **Compose and build android app with new architecture principles** |
| [*Read article*](https://developersbreach.com/compose-android-app-architecture/) |

| <img src="/assets/custom-animations-blog-preview-image.png" alt="Article banner" width="600" /> |
| :-: |
| **Custom shape animations pulsating circles on canvas in compose android** |
| [*Read article*](https://developersbreach.com/custom-shape-animations-pulsating-circles-canvas-compose/) |

| <img src="/assets/textfield-blog-preview-image.png" alt="Article banner" width="600" /> |
| :-: |
| **Search with TextField in list Compose Android Jetpack** |
| [*Read article*](https://developersbreach.com/search-with-textfield-list-compose/) |

![AppPreview](/assets/main.gif)

## :bulb: Motivation and Context

`Jetpack Compose` is Android’s modern toolkit for building native UI. It enables you to quickly
bring your app to life with less code, powerful tools, and intuitive Kotlin APIs.

Understanding to implement own `Theme` `Shape` `Typography` `Color` has became bit easier by
referring to lot of official jetpack compose samples which are available in GitHub.

Best of all we got to do this in `Kotlin` way. Excited and long way to go from here.

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
