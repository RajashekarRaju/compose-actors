package com.developersbreach.ktlint.rules

import com.pinterest.ktlint.rule.engine.core.api.ElementType
import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

public class NoHardcodedStringsRule :
    Rule(
        ruleId = RuleId("compose-actors:no-hardcoded-strings"),
        about = About(
            maintainer = "Compose Actors",
            repositoryUrl = "https://github.com/compose-actors/compose-actors"
        )
    ) {

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        when (node.elementType) {
            ElementType.FUN -> {
                // Check if this function is annotated with @Composable
                if (isComposableFunction(node)) {
                    // Skip preview functions as they contain test data
                    if (isPreviewFunction(node)) {
                        return
                    }
                    
                    // Check for hardcoded strings in this function
                    checkForHardcodedStrings(node, emit)
                }
            }
            ElementType.PROPERTY -> {
                // Check for hardcoded strings in top-level property declarations in UI files
                // Skip companion object constants as they're typically not user-facing
                // But include private const val declarations as they might end up in UI
                if (isInUIFile(node) && !isInCompanionObject(node)) {
                    checkPropertyForHardcodedStrings(node, emit)
                }
            }
        }
    }

    private fun isComposableFunction(node: ASTNode): Boolean {
        // Look for @Composable annotation
        val modifierList = node.children().firstOrNull { it.elementType == ElementType.MODIFIER_LIST }
        return modifierList?.children()?.any { annotationEntry ->
            annotationEntry.elementType == ElementType.ANNOTATION_ENTRY &&
                annotationEntry.text.contains("Composable")
        } ?: false
    }

    private fun isPreviewFunction(node: ASTNode): Boolean {
        // Look for @Preview or @PreviewLightDark annotations
        val modifierList = node.children().firstOrNull { it.elementType == ElementType.MODIFIER_LIST }
        return modifierList?.children()?.any { annotationEntry ->
            annotationEntry.elementType == ElementType.ANNOTATION_ENTRY &&
                (annotationEntry.text.contains("Preview") || 
                 annotationEntry.text.contains("PreviewLightDark"))
        } ?: false
    }

    private fun checkForHardcodedStrings(
        node: ASTNode,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        node.children().forEach { child ->
            when (child.elementType) {
                ElementType.STRING_TEMPLATE -> {
                    checkStringTemplate(child, emit)
                }
                ElementType.PROPERTY -> {
                    // Check local val declarations in Composables
                    checkPropertyForHardcodedStrings(child, emit)
                }
                else -> checkForHardcodedStrings(child, emit)
            }
        }
    }

    private fun extractStringContent(node: ASTNode): String? {
        // Extract the actual string content from the string template
        val text = node.text
        if (text.startsWith("\"") && text.endsWith("\"") && text.length > 2) {
            return text.substring(1, text.length - 1)
        }
        return null
    }

    private fun isUserVisibleString(text: String): Boolean {
        // Skip template strings (strings starting with $ or containing ${})
        if (text.startsWith("$") || text.contains("\${")) {
            return false
        }

        // Skip technical strings
        val technicalPatterns = listOf(
            "http", "https", "www", ".com", ".org", ".net", // More specific domain patterns
            "TAG", "DEBUG", "ERROR", "WARN", "INFO", "VERBOSE",
            "application/", "text/", "image/", "video/", "audio/",
            "yyyy-MM-dd", "HH:mm:ss", "yyyy", "MM", "dd", "HH", "mm", "SSS", // More specific date/time patterns
            "TestTag:", "cd_", "ContentDescription",
            "Bearer ", "Authorization", "Content-Type",
            "UTF-8", "ISO-", "GMT", "UTC",
            "android.", "androidx.", "kotlin.", "java.",
            "SELECT", "INSERT", "UPDATE", "DELETE", "FROM", "WHERE",
            "CREATE TABLE", "DROP TABLE", "ALTER TABLE",
            "ktlint:", "BanParcelableUsage", "modifier =", "code =",
            "biography =", "dateOfBirth =", "placeOfBirth =",
            // API endpoint patterns
            "person/", "movie/", "trending/", "search/", "discover/",
            "credits?", "popular?", "week?", "day?", "api_key=",
        )

        if (technicalPatterns.any { text.contains(it, ignoreCase = true) }) {
            return false
        }

        // Skip single words that are likely technical
        if (!text.contains(" ") && (
                text.matches(Regex("^[A-Z_]+$")) || // ALL_CAPS constants
                    text.matches(Regex("^[a-z_]+$")) || // snake_case identifiers
                    text.matches(Regex("^[a-zA-Z]+[0-9]+$")) || // alphanumeric identifiers
                    text.length < 4 // Very short strings
            )
        ) {
            return false
        }

        // Skip strings that are mostly whitespace or formatting
        // Also allow empty strings as they're often intentional (e.g., empty contentDescription for decorative images)
        if (text.trim().isEmpty() || text.matches(Regex("^[\\s,=]+$"))) {
            return false
        }

        // Check if it contains letters and is likely user-visible
        return text.any { it.isLetter() } && text.length > 2
    }

    private fun checkStringTemplate(
        node: ASTNode,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        // Handle both simple strings and template strings with hardcoded parts
        val text = node.text
        
        // Check if this is a template string (contains ${} or $variable patterns)
        val isTemplateString = text.contains("\${") || 
                              (text.contains("$") && text.matches(Regex(".*\\$[a-zA-Z_][a-zA-Z0-9_]*.*")))
        
        // For simple quoted strings (no template interpolation)
        if (text.startsWith("\"") && text.endsWith("\"") && !isTemplateString) {
            val stringContent = extractStringContent(node)
            if (stringContent != null && isUserVisibleString(stringContent)) {
                emit(
                    node.startOffset,
                    "Hardcoded string \"$stringContent\" found in Composable function. Consider using string resources for localization.",
                    false
                )
            }
        }
        // For template strings, check for hardcoded literal parts
        else if (isTemplateString) {
            checkTemplateStringLiterals(node, emit)
        }
    }

    private fun checkTemplateStringLiterals(
        node: ASTNode,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        // Extract literal parts from template strings like "$userName logged in"
        val text = node.text
        if (text.startsWith("\"") && text.endsWith("\"")) {
            val content = text.substring(1, text.length - 1)
            
            // Split by both ${...} and $variable patterns and check literal parts
            val parts = content.split(Regex("\\$\\{[^}]*\\}|\\$[a-zA-Z_][a-zA-Z0-9_]*"))
            parts.forEach { part ->
                if (part.isNotEmpty() && isUserVisibleString(part)) {
                    emit(
                        node.startOffset,
                        "Hardcoded string literal \"$part\" found in template string. Consider using string resources for localization.",
                        false
                    )
                }
            }
        }
    }

    private fun checkPropertyForHardcodedStrings(
        node: ASTNode,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        // Check if this property has a string initializer
        node.children().forEach { child ->
            if (child.elementType == ElementType.STRING_TEMPLATE) {
                checkStringTemplate(child, emit)
            } else {
                checkPropertyForHardcodedStrings(child, emit)
            }
        }
    }

    private fun isInUIFile(node: ASTNode): Boolean {
        // Get the root node to access the entire file content
        var root = node
        while (root.treeParent != null) {
            root = root.treeParent
        }
        val fileText = root.text
        
        // Skip build scripts - they contain these patterns
        if (fileText.contains("build.gradle") || 
            fileText.contains("@file:Suppress(\"UnstableApiUsage\")") ||
            (fileText.contains("dependencies {") && fileText.contains("implementation("))) {
            return false
        }
        
        // Skip settings files
        if (fileText.contains("settings.gradle") || fileText.contains("pluginManagement")) {
            return false
        }
        
        // Skip fake data files - they contain test/mock data, not user-facing strings
        // But allow our test file for testing the rule
        if ((fileText.contains("package com.developersbreach.composeactors.data.datasource.fake") ||
            fileText.contains("data/datasource/fake") ||
            fileText.contains("AmplifyConfigProvider")) &&
            !fileText.contains("package com.developersbreach.composeactors.test")) {
            return false
        }
        
        // For actual Kotlin source files, assume they could be UI files
        return true
    }

    private fun isInCompanionObject(node: ASTNode): Boolean {
        // Walk up the AST to see if this node is inside a companion object
        var parent = node.treeParent
        while (parent != null) {
            if (parent.elementType == ElementType.OBJECT_DECLARATION) {
                // Check if this object declaration is a companion object
                val objectText = parent.text
                if (objectText.contains("companion object")) {
                    return true
                }
            }
            parent = parent.treeParent
        }
        return false
    }
}