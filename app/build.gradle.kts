plugins {
    // Default
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    // Google
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "my.dahr.monopolyone"
    compileSdk = 34

    defaultConfig {
        applicationId = "my.dahr.monopolyone"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
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
    implementation(project(path = ":data"))

    // Junit
    testImplementation(libs.junit)

    // *** AndroidX ***

    // Default
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Fragment
    implementation(libs.androidx.fragment.ktx)

    // Lifecycles
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Work Manager
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)

    // *** Google ***

    // Material
    implementation(libs.google.material)

    // Firebase TODO: Add Firebase dependencies

    // Hilt
    ksp(libs.google.hilt.compiler)
    implementation(libs.google.hilt)

    // GSON TODO: Remove from :app
    implementation(libs.google.gson)

    // *** KotlinX ***

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // *** Other ***

    // Retrofit + OkHTTP TODO: Remove from :app
    implementation(libs.retrofit)
    implementation(libs.gson.converter)
    implementation(libs.okhttp)

    // Glide
    implementation(libs.glide)

    // Loading button
    implementation(libs.loadingBt)

}