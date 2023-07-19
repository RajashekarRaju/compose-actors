@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.gms)
    alias(libs.plugins.com.google.firebase)
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.developersbreach.composeactors"
        minSdk = 24
        targetSdk = 33
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

    kotlinOptions {
        jvmTarget = "1.8"
        // To mark experimental features api
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
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

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(platform(libs.kotlin.bom))

    implementation(libs.androidx.core.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.com.google.android.material)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
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
    kapt(libs.androidx.room.room.compiler)

    // hilt
    implementation(libs.com.google.dagger.hilt.android)
    implementation(libs.androidx.hilt.hilt.navigation.compose)
    kapt(libs.com.google.dagger.hilt.android.compiler)

    // Paging
    implementation(libs.androidx.paging.paging.compose)
    implementation(libs.androidx.paging.paging.runtime.ktx)

    testImplementation(libs.androidx.compose.ui.ui.test.junit4)
    testImplementation(libs.io.mockk)

    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.compose.ui.ui.test.junit4)

    debugImplementation(libs.androidx.compose.ui.ui.test.manifest)
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
