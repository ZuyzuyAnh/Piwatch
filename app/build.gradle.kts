plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.piwatch"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.piwatch"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        android.buildFeatures.buildConfig = true
        buildConfigField("String", "TMDB_KEY", project.properties["TMDB_KEY"].toString())
        buildConfigField("String", "BASE_URL", project.properties["BASE_URL"].toString())
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        vectorDrawables.useSupportLibrary = true

    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("com.google.firebase:firebase-firestore:24.11.0")
    val nav_version = "2.5.3"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.navigation:navigation-compose:$nav_version")

    //Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")


    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    // Moshi
    implementation ("com.squareup.moshi:moshi-kotlin:1.14.0")
    kapt ("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")

    // Coroutine
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")

    // Room and room pagination
    implementation ("androidx.room:room-runtime:2.5.1")
    kapt ("androidx.room:room-compiler:2.5.1")
    implementation ("androidx.room:room-ktx:2.5.1")
    implementation ("androidx.room:room-paging:2.5.1")


    // multidex
    implementation ("androidx.multidex:multidex:2.0.1")
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))


    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:converter-gson:2.10.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
    implementation("androidx.hilt:hilt-work:1.2.0")
    // When using Kotlin.
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    implementation("br.com.devsrsouza.compose.icons:font-awesome:1.1.0")
    implementation("io.coil-kt:coil-compose:2.4.0")
    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries
    implementation("androidx.compose.material:material-icons-extended:1.6.5")

    //YoutubeView
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")

    //SwipeReFresh
    implementation("com.google.accompanist:accompanist-swiperefresh:0.27.0")

    //Permission
    implementation("com.google.accompanist:accompanist-permissions:0.31.1-alpha")

    //SplashScreen
    implementation("androidx.core:core-splashscreen:1.0.0")

    //Pager and Indicators
    implementation("com.google.accompanist:accompanist-pager:0.34.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.34.0")

    //DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
}