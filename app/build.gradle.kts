plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.dagger)
    alias(libs.plugins.kotlin.serialization)

    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.glmusic"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.glmusic"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.converter)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging.interceptor)

    // Dependency Injection (Dagger Hilt)
    implementation(libs.dagger.hilt)

    kapt(libs.dagger.hilt.compiler)

    // Hilt ViewModel
    implementation(libs.hilt.view.model)

    // Navigation
    implementation(libs.navigation.compose)
    implementation(libs.navigation.animation)

    // JSON and Serialization
    implementation(libs.kotlinx.serialization.json)

    // Media3
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)

    // Image Loading (Coil)
    implementation(libs.coil)
}

kapt {
    correctErrorTypes = true
}