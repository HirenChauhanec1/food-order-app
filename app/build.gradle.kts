plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("androidx.navigation.safeargs.kotlin")
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.codewithhiren.foodorderapplatest"
    compileSdk = 35


    defaultConfig {
        applicationId = "com.codewithhiren.foodorderapplatest"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        viewBinding = true
        dataBinding = true
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation (libs.ssp.android)
    implementation (libs.sdp.android)

    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)

    implementation (libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation (libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.jackson.databind)  // for @JsonProperty

    implementation (libs.glide)
    implementation (libs.picasso)
}