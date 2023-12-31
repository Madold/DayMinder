plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.markusw.dayminder"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.markusw.dayminder"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isShrinkResources = true
            isDebuggable = false
        }
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
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
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val composeBomVersion = "2023.09.00"
    val roomVersion = "2.5.2"
    val daggerHiltVersion = "2.48"
    val composeNavigationVersion = "2.7.2"
    val hiltNavigationComposeVersion = "1.0.0"
    val lifecycleRuntimeComposeVersion = "2.6.2"
    val timberVersion = "5.0.1"
    val loggerVersion = "2.2.0"
    val lottieComposeVersion = "6.1.0"
    val firebaseBomVersion = "32.3.1"
    val splashScreenApiVersion = "1.0.1"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    //Room
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    //Dagger Hilt
    implementation("com.google.dagger:hilt-android:$daggerHiltVersion")
    ksp("com.google.dagger:hilt-android-compiler:$daggerHiltVersion")

    //Navigation
    implementation("androidx.navigation:navigation-compose:$composeNavigationVersion")

    //Hilt Navigation Compose
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion")

    //Lifecycle runtime
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleRuntimeComposeVersion")

    //Timber
    implementation("com.jakewharton.timber:timber:$timberVersion")

    //Logger
    implementation("com.orhanobut:logger:$loggerVersion")

    //Lottie
    implementation("com.airbnb.android:lottie-compose:$lottieComposeVersion")

    //Firebase
    //Bom
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:$firebaseBomVersion"))
    //Analytics
    implementation("com.google.firebase:firebase-analytics-ktx")
    //Crashlytics
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    //Splash screen API
    implementation("androidx.core:core-splashscreen:$splashScreenApiVersion")


    //Test dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}