package com.developersbreach.composeactors.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertFalse

/**
 * Usage:
 * - `componentName`: The fully qualified name of the component to check (e.g., [androidx.compose.material.Text]).
 * - `excludePaths`: Paths to files within `designSystem` where direct usage is allowed.
 */
fun checkNoDirectUsageExceptAllowed(
    componentName: String,
    excludePaths: Array<String>
) {
    Konsist.scopeFromProject()
        .files
        .filter { file ->
            val normalizedFilePath = file.path.replace("\\", "/").lowercase()
            excludePaths.none { normalizedFilePath.contains(it.replace("\\", "/").lowercase()) }
        }
        .flatMap { file ->
            file.imports.filter { it.name == componentName }.map { file }
        }
        .assertFalse { true }
}