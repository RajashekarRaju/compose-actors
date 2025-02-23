package com.developersbreach.composeactors.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertFalse
import org.junit.Test

/**
 * These tests ensure that Material Design components from Jetpack Compose are used
 * only within the `designSystem` package.
 * These tests checks for direct imports of specified components outside the allowed
 * files, ensuring consistency and maintainability across the UI.
 *
 * Usage:
 * - `materialComponent`: The fully qualified name of the component to check (e.g., [androidx.compose.material.Text]).
 * - `excludePaths`: Paths to files within `designSystem` where direct usage is allowed.
 */
class DesignSystemTest {

    @Test
    fun `no direct usage of androidx compose textField should be allowed except designSystem`() {
        checkNoDirectUsageExceptDesignSystem(
            materialComponent = "androidx.compose.material.TextField",
            excludePaths = arrayOf(
                "design-system/src/main/java/com/developersbreach/designsystem/components/TextField.kt"
            )
        )
    }

    @Test
    fun `no direct usage of androidx compose snackbar should be allowed except designSystem`() {
        checkNoDirectUsageExceptDesignSystem(
            materialComponent = "androidx.compose.material.Snackbar",
            excludePaths = arrayOf(
                "design-system/src/main/java/com/developersbreach/designsystem/components/Snackbar.kt"
            )
        )
    }

    @Test
    fun `no direct usage of androidx compose surface should be allowed except designSystem`() {
        checkNoDirectUsageExceptDesignSystem(
            materialComponent = "androidx.compose.material.Surface",
            excludePaths = arrayOf(
                "design-system/src/main/java/com/developersbreach/designsystem/components/Surface.kt"
            )
        )
    }

    @Test
    fun `no direct usage of androidx compose card should be allowed except designSystem`() {
        checkNoDirectUsageExceptDesignSystem(
            materialComponent = "androidx.compose.material.Card",
            excludePaths = arrayOf(
                "design-system/src/main/java/com/developersbreach/designsystem/components/Card.kt"
            )
        )
    }

    @Test
    fun `no direct usage of androidx compose image should be allowed except designSystem`() {
        checkNoDirectUsageExceptDesignSystem(
            materialComponent = "androidx.compose.foundation.Image",
            excludePaths = arrayOf(
                "design-system/src/main/java/com/developersbreach/designsystem/components/Image.kt"
            )
        )
    }

    @Test
    fun `no direct usage of androidx compose icon should be allowed except designSystem`() {
        checkNoDirectUsageExceptDesignSystem(
            materialComponent = "androidx.compose.material.Icon",
            excludePaths = arrayOf(
                "design-system/src/main/java/com/developersbreach/designsystem/components/Icon.kt"
            )
        )
    }

    @Test
    fun `no direct usage of androidx compose iconButton should be allowed except designSystem`() {
        checkNoDirectUsageExceptDesignSystem(
            materialComponent = "androidx.compose.material.IconButton",
            excludePaths = arrayOf(
                "design-system/src/main/java/com/developersbreach/designsystem/components/IconButton.kt"
            )
        )
    }

    @Test
    fun `no direct usage of androidx compose scaffold should be allowed except designSystem`() {
        checkNoDirectUsageExceptDesignSystem(
            materialComponent = "androidx.compose.material.Scaffold",
            excludePaths = arrayOf(
                "design-system/src/main/java/com/developersbreach/designsystem/components/Scaffold.kt"
            )
        )
    }

    @Test
    fun `no direct usage of androidx compose divider should be allowed except designSystem`() {
        checkNoDirectUsageExceptDesignSystem(
            materialComponent = "androidx.compose.material.Divider",
            excludePaths = arrayOf(
                "design-system/src/main/java/com/developersbreach/designsystem/components/Divider.kt"
            )
        )
    }

    private fun checkNoDirectUsageExceptDesignSystem(
        materialComponent: String,
        excludePaths: Array<String>
    ) {
        Konsist.scopeFromProject()
            .files
            .filter { file ->
                val normalizedFilePath = file.path.replace("\\", "/").lowercase()
                excludePaths.none { normalizedFilePath.contains(it.replace("\\", "/").lowercase()) }
            }
            .flatMap { file ->
                file.imports.filter { it.name == materialComponent }.map { file }
            }
            .assertFalse {
                true // Ensure that the Material component is not directly used outside the designSystem
            }
    }
}