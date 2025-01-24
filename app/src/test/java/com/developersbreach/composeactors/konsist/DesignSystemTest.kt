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
                excludePaths.all { !file.path.contains(it) }
            }
            .flatMap { file ->
                file.imports.filter { it.name == materialComponent }.map { file }
            }
            .assertFalse {
                true // Ensure that the Material component is not directly used outside the designSystem
            }
    }
}