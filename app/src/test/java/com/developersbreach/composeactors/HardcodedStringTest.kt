package com.developersbreach.composeactors

import org.junit.Test
import java.io.File

class HardcodedStringTest {
    
    @Test
    fun `Composable functions should not contain hardcoded user-visible strings`() {
        val violations = mutableListOf<String>()
        val projectRoot = File(System.getProperty("user.dir"))
        
        // Find all Kotlin files in the main source directory
        val mainSrcDir = File(projectRoot, "src/main")
        
        if (mainSrcDir.exists()) {
            mainSrcDir.walkTopDown()
                .filter { it.isFile && it.extension == "kt" }
                .filter { !it.path.contains("test", ignoreCase = true) }
                .filter { !it.path.contains("Test") }
                .filter { !it.path.contains("fake") }
                .forEach { file ->
                    val content = file.readText()
                    val hardcodedStrings = findHardcodedStrings(content)
                    
                    if (hardcodedStrings.isNotEmpty()) {
                        // Check if this file contains Composable functions
                        if (content.contains("@Composable")) {
                            // Skip preview functions as they contain test data
                            val contentWithoutPreviews = removePreviewFunctions(content)
                            val hardcodedStringsWithoutPreviews = findHardcodedStrings(contentWithoutPreviews)
                            
                            if (hardcodedStringsWithoutPreviews.isNotEmpty()) {
                                val relativePath = file.relativeTo(projectRoot).path
                                violations.add("File $relativePath contains hardcoded strings: $hardcodedStringsWithoutPreviews")
                            }
                        }
                    }
                }
        }
        
        if (violations.isNotEmpty()) {
            violations.forEach { println("[DEBUG_LOG] $it") }
            throw AssertionError("Found ${violations.size} files with hardcoded strings in Composable functions")
        }
    }
    
    private fun findHardcodedStrings(text: String): List<String> {
        val stringRegex = """"([^"]{3,})"""".toRegex()
        val matches = stringRegex.findAll(text)
        
        return matches
            .map { it.groupValues[1] }
            .filter { isUserVisibleString(it) }
            .toList()
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
            "biography =", "dateOfBirth =", "placeOfBirth ="
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
        )) {
            return false
        }
        
        // Skip strings that are mostly whitespace or formatting
        if (text.trim().isEmpty() || text.matches(Regex("^[\\s,=]+$"))) {
            return false
        }
        
        // Check if it contains letters and is likely user-visible
        return text.any { it.isLetter() } && text.length > 2
    }
    
    private fun removePreviewFunctions(content: String): String {
        // Remove preview functions that contain test data
        val previewRegex = """@PreviewLightDark\s*@Composable[^}]*fun\s+\w+Preview[^{]*\{[^}]*\}""".toRegex(RegexOption.DOT_MATCHES_ALL)
        return previewRegex.replace(content, "")
    }
    
    private fun isLikelyUserVisible(text: String): Boolean {
        // More strict check for non-UI functions
        // Look for complete sentences or phrases that users might see
        return text.contains(" ") && 
               text.any { it.isLetter() } && 
               (text.contains("!") || text.contains("?") || text.contains(".") || text.length > 10)
    }
}