package com.developersbreach.composeactors.konsist

import org.junit.Test

class PreviewAnnotationRestrictionTest {

    @Test
    fun `no direct usage of androidx compose Preview should be allowed except PreviewLightDark`() {
        checkNoDirectUsageExceptAllowed(
            componentName = "androidx.compose.ui.tooling.preview.Preview",
            excludePaths = arrayOf(
                "compose-actors/app/src/main/java/com/developersbreach/composeactors/annotations/PreviewLightDark.kt",
            ),
        )
    }
}