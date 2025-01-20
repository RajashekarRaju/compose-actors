plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.com.google.ksp) apply false
    alias(libs.plugins.com.google.gms) apply false
    alias(libs.plugins.com.google.firebase) apply false
    alias(libs.plugins.com.diffplug.spotless)
    alias(libs.plugins.com.github.ben.manes.versions)
    alias(libs.plugins.nl.littlerobots.version.catalog.update)
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}

buildscript {
    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
    }
}

spotless {
    // optional: limit format enforcement to just the files changed by this feature branch
    //ratchetFrom 'origin/master'

    kotlin {
        // spotless:off and spotless:on
        // toggleOffOn()

        target("**/*.kt")
        targetExclude("$buildDir/**/*.kt")
        targetExclude("bin/**/*.kt")
        ktlint("0.48.1")
        ktfmt()
    }

    // Disabling warnings and error messages
    // enforceCheck false
}

tasks.withType(com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask::class) {
    fun isStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        return stableKeyword || regex.matches(version)
    }
    gradleReleaseChannel = "current"
    revision = "release"
    rejectVersionIf { !isStable(candidate.version) }
}
