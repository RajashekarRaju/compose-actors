@file:Suppress("UnstableApiUsage")

import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.ksp)
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.com.google.gms)
    alias(libs.plugins.com.google.firebase)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.serialization)
    alias(libs.plugins.screenshot)
    alias(libs.plugins.ktlint)
}

android {
    compileSdk = 35

    defaultConfig {
        applicationId = "com.developersbreach.composeactors"
        minSdk = 24
        targetSdk = 34
        versionCode = 3
        versionName = "0.3.0"

        vectorDrawables {
            useSupportLibrary = true
        }

        // signingConfig signingConfigs.debug
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["clearPackageData"] = "true"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    experimentalProperties["android.experimental.enableScreenshotTest"] = true
    kotlinOptions {
        jvmTarget = "1.8"
        // To mark experimental features api
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.developersbreach.composeactors"

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

ktlint {
    android = true
    ignoreFailures = false
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.SARIF)
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(platform(libs.kotlin.bom))
    implementation(project(":design-system"))

    implementation(libs.androidx.core.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.com.google.android.material)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.ui.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.ui.tooling)
    implementation(libs.androidx.activity.activity.compose)

    // Observe state and livedata
    implementation(libs.androidx.compose.runtime.runtime.livedata)
    implementation(libs.androidx.lifecycle.lifecycle.runtime.ktx)

    // Constraint Layout
    implementation(libs.androidx.constraintlayout.constraintlayout.compose)

    // Navigation
    implementation(libs.androidx.navigation.navigation.compose)

    // Timber for logging
    implementation(libs.com.jakewharton.timber)

    // Coil for image loading
    implementation(libs.io.coil.kt.coil.compose)

    // Palette
    implementation(libs.androidx.palette.palette.ktx)

    // Room
    implementation(libs.androidx.room.room.runtime)
    implementation(libs.androidx.room.room.ktx)
    implementation(libs.com.google.firebase.analytics)
    implementation(libs.com.google.firebase.crashlytics)
    ksp(libs.androidx.room.room.compiler)

    implementation(libs.arrow.core)
    implementation(libs.arrow.fx.coroutines)

    // hilt
    implementation(libs.com.google.dagger.hilt.android)
    implementation(libs.androidx.hilt.hilt.navigation.compose)
    ksp(libs.com.google.dagger.hilt.android.compiler)
    ksp(libs.hilt.androidx.compiler)

    // Paging
    implementation(libs.androidx.paging.paging.compose)
    implementation(libs.androidx.paging.paging.runtime.ktx)

    implementation(platform(libs.ktor.bom))
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.serialization)

    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.androidx.compose.ui.ui.test.junit4)
    testImplementation(libs.io.mockk)
    testImplementation(libs.konsist)

    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.compose.ui.ui.test.junit4)

    debugImplementation(libs.androidx.compose.ui.ui.test.manifest)

    screenshotTestImplementation(libs.androidx.compose.ui.tooling)
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}