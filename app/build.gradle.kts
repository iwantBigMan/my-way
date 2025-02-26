import org.jetbrains.kotlin.gradle.model.Kapt

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}

android {
    namespace = "com.hyeon.side.sideapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hyeon.side.sideapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "CLIENT_",
            "\"85624277913-e5irs6puevdbprg5beic43c9kih35528.apps.googleusercontent.com\""
        )
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.google.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.retrofit2)
    implementation(libs.retrofit2ConverterGson)
    implementation(libs.google.auth)
    implementation(libs.view.model)
    implementation(libs.view.pager2)
    implementation(libs.fragment.ktx)
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)


}
apply(plugin = "dagger.hilt.android.plugin")