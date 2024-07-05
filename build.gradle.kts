// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Default
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

    // Google
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
    alias(libs.plugins.androidLibrary) apply false
}