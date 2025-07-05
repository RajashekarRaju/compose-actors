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
        if (node.elementType == ElementType.FUN) {
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
                    val stringContent = extractStringContent(child)
                    if (stringContent != null && isUserVisibleString(stringContent)) {
                        emit(
                            child.startOffset,
                            "Hardcoded string \"$stringContent\" found in Composable function. Consider using string resources for localization.",
                            false
                        )
                    }
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
            "http", "https", "www", "com", "org", "net",
            "TAG", "DEBUG", "ERROR", "WARN", "INFO", "VERBOSE",
            "application/", "text/", "image/", "video/", "audio/",
            "yyyy", "MM", "dd", "HH", "mm", "ss", "SSS",
            "TestTag:", "cd_", "ContentDescription",
            "Bearer ", "Authorization", "Content-Type",
            "UTF-8", "ISO-", "GMT", "UTC",
            "android.", "androidx.", "kotlin.", "java.",
            "SELECT", "INSERT", "UPDATE", "DELETE", "FROM", "WHERE",
            "CREATE TABLE", "DROP TABLE", "ALTER TABLE",
            "ktlint:", "BanParcelableUsage", "modifier =", "code =",
            "biography =", "dateOfBirth =", "placeOfBirth =",
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
        if (text.trim().isEmpty() || text.matches(Regex("^[\\s,=]+$"))) {
            return false
        }

        // Check if it contains letters and is likely user-visible
        return text.any { it.isLetter() } && text.length > 2
    }
}