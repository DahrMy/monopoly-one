plugins {
    // Default
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    // Google
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)

    id("kotlin-parcelize")
}

android {
    namespace = "my.dahr.monopolyone.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(project(path = ":domain"))

    // Junit
    testImplementation(libs.junit)

    // *** AndroidX ***

    // Junit
    androidTestImplementation(libs.androidx.junit)

    // Room
    ksp(libs.androidx.room.compiler)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    // *** Google ***

    // Hilt
    ksp(libs.google.hilt.compiler)
    implementation(libs.google.hilt)

    // GSON
    implementation(libs.google.gson)

    // *** KotlinX ***

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // *** Other ***

    // Retrofit + OkHTTP
    implementation(libs.retrofit)
    implementation(libs.gson.converter)
    implementation(libs.okhttp)

}