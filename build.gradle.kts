// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}

// --------------------------------------
// If you still use buildscript {}, add it here:
buildscript {
    repositories {
        google()
        mavenCentral()
        // Add JitPack if you need libraries hosted there (e.g. AnyChart)
     maven { setUrl("https://jitpack.io") }
    }

    // If needed, declare classpath for old Gradle usage:
    // dependencies {
    //     classpath "com.android.tools.build:gradle:8.1.0"
    //     // ...
    // }
}

// --------------------------------------
// For sub-projects (modules) in older AGP structures:

// If your project is using the newer settings.gradle approach, you might not need the above allprojects block.
