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
        targetSdk = 34
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

    // Room
    ksp(libs.androidx.room.compiler)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    // *** Google ***

    // Material
    implementation(libs.material)

    // Firebase

    // Hilt
    ksp(libs.hilt.compiler)
    implementation(libs.hilt)

    // GSON
    implementation(libs.gson)

    // *** KotlinX ***

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // *** Other ***

    // Retrofit + OkHTTP
    implementation(libs.retrofit)
    implementation(libs.gson.converter)
    implementation(libs.okhttp)

    // Glide
    implementation(libs.glide)

    // Loading button
    implementation(libs.loadingBt)

}