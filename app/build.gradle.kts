import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp) //use ksp to replace kapt

    // Kotlin serialization plugin for type safe routes and navigation arguments
    kotlin("plugin.serialization") version "2.1.10"
    alias(libs.plugins.google.gms.google.services)// push noti

}


val apiKey: String = project.rootProject.file("local.properties")
    .inputStream()
    .use { input ->
        Properties().apply { load(input) }
    }
    .getProperty("RAPID_API_KEY")


android {
    namespace = "com.hugo.oversteerf1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hugo.oversteerf1"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "RAPID_API_KEY", "\"$apiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    packaging {
        resources {
            excludes += "/META-INF/versions/9/OSGI-INF/MANIFEST.MF"
        }
    }
    
}

dependencies {

    implementation(project(":utilities"))
    implementation(project(":design"))
    implementation(project(":datasource"))
    implementation(project(":schedule"))
    implementation(project(":standings"))
    implementation(project(":result"))
    implementation(project(":authentication"))
    implementation(project(":settings"))
    implementation(project(":notifications"))
    implementation(project(":network"))


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.corountine.android)
    implementation(libs.hilt)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    implementation(libs.splash.screen)

    implementation(libs.coil)
    implementation(libs.coilNetwork)

    implementation(libs.lottie)

    implementation(libs.androidx.compose.runtime)

    // Navigation animation
    implementation(libs.navigation.compose)

    //Serialization Navigation
    implementation(libs.kotlinx.serialization.json)
    //implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.8.1")


    //retrofit + okhttp3 for API fetching
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging.interceptor)

    //Supabase
    implementation(platform(libs.supabase.bom))
    implementation(libs.postgrest.kt)
    implementation(libs.ktor.client.android)




}