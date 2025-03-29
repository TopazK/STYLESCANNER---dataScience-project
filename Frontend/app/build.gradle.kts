plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.stylescannerapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.stylescannerapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.play.services.location)
    implementation (libs.gson)
    // Retrofit for networking
    implementation (libs.retrofit)

    // Gson for JSON parsing
    implementation (libs.converter.gson)

    // Gson library (optional if you need custom parsing)
    implementation (libs.gson)

    // OkHttp Logging (optional for debugging)
    implementation (libs.logging.interceptor)


}