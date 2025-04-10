// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven {setUrl("https://jitpack.io")}
    }

    // If needed, declare classpath for old Gradle usage:
    // dependencies {
    //     classpath "com.android.tools.build:gradle:8.1.0"
    //     // ...
    // }
}