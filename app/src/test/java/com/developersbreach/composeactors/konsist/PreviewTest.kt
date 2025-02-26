package com.developersbreach.composeactors.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertFalse
import org.junit.Test

class PreviewTest {

    @Test
    fun `no direct usage of androidx compose Preview should be allowed except designSystem`() {
        checkNoDirectUsageExceptPreviewLightDark(
            previewAnnotation = "androidx.compose.ui.tooling.preview.Preview",
            excludePaths = arrayOf(
                "compose-actors/app/src/main/java/com/developersbreach/composeactors/annotations/PreviewLightDark.kt",
                "compose-actors/app/src/screenshotTest/kotlin/AboutScreenScreenshotTest.kt",
                "compose-actors/app/src/screenshotTest/kotlin/ActorDetailsScreenshotTest.kt",
                "compose-actors/app/src/screenshotTest/kotlin/FavoritesScreenScreenshotTest.kt",
                "compose-actors/app/src/screenshotTest/kotlin/HomeScreenScreenshotTest.kt",
                "compose-actors/app/src/screenshotTest/kotlin/MovieDetailsScreenshotTest.kt",
                "compose-actors/app/src/screenshotTest/kotlin/OptionsModalSheetContentScreenshotTest.kt",
                "compose-actors/app/src/screenshotTest/kotlin/SearchScreenScreenshotTest.kt",
                "compose-actors/app/src/screenshotTest/kotlin/SheetContentActorDetailsScreenshotTest.kt",
                "compose-actors/app/src/screenshotTest/kotlin/SheetContentMovieDetailsScreenshotTest.kt",
                "compose-actors/app/src/main/java/com/developersbreach/composeactors/ui/screens/actorDetails/composables/ActorRoundProfile.kt",
                "compose-actors/app/src/main/java/com/developersbreach/composeactors/ui/screens/actorDetails/composables/ActorInfoHeader.kt",
                "compose-actors/app/src/main/java/com/developersbreach/composeactors/ui/screens/home/HomeBottomBar.kt",
                "compose-actors/app/src/main/java/com/developersbreach/composeactors/ui/screens/home/HomeSnackBar.kt",
                "compose-actors/app/src/main/java/com/developersbreach/composeactors/ui/screens/home/HomeTopAppBar.kt",
                "compose-actors/app/src/main/java/com/developersbreach/composeactors/ui/screens/home/tabs/TvShowsTabContent.kt",
                "compose-actors/app/src/main/java/com/developersbreach/composeactors/ui/screens/home/tabs/MoviesTabContent.kt",
            )
        )
    }

    private fun checkNoDirectUsageExceptPreviewLightDark(
        previewAnnotation: String,
        excludePaths: Array<String>
    ) {
        Konsist.scopeFromProject()
            .files
            .filter { file ->
                val normalizedFilePath = file.path.replace("\\", "/").lowercase()
                excludePaths.none { normalizedFilePath.contains(it.replace("\\", "/").lowercase()) }
            }
            .flatMap { file ->
                file.imports.filter { it.name == previewAnnotation }.map { file }
            }
            .assertFalse {
                true
            }
    }
}