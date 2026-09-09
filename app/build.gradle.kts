import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    //Hilt
    alias(libs.plugins.hilt)
    //Navigation 3
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.appdistribution)
    alias(libs.plugins.detekt)
}

val properties = Properties()
rootProject.file("local.properties").takeIf { it.exists() }?.inputStream()?.use {
    properties.load(it)
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) load(file.inputStream())
}

android {
    namespace = "com.example.movievault"
    compileSdk = 36

    buildFeatures {
        compose = true
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.movievault"
        minSdk = 32
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "TMDB_API_KEY",
            "\"${properties.getProperty("TMDB_API_KEY") ?: throw GradleException("TMDB_API_KEY not found in local.properties")}\""
        )
    }

    signingConfigs {
        create("release") {
            storeFile = file(localProperties["KEYSTORE_PATH"] as String)
            storePassword = localProperties["KEYSTORE_PASSWORD"] as String
            keyAlias = localProperties["KEY_ALIAS"] as String
            keyPassword = localProperties["KEY_PASSWORD"] as String
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            firebaseAppDistribution {
                artifactType = "APK"
                groups = "qa-team"
                releaseNotes = "Debug build — latest changes"
            }
        }

        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            firebaseAppDistribution {
                artifactType = "APK"
                groups = "qa-team"
                releaseNotes = "Release build — ready for testing"
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.ui.text)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.compose.ui.text.google.fonts)
    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    //Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    //OkHttp
    implementation(libs.okhttp.logging)
    //Coil
    implementation(libs.coil.compose)
    //Paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
    //Navigation 3
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.material3.adaptive.navigation3)
    implementation(libs.kotlinx.serialization.core)
    //Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.accompanist.systemuicontroller)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.timber)
    implementation(libs.firebase.messaging)
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.7")
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
}