import java.util.Properties

val configProperties = getProperties("config.properties")

fun getProperties(fileName: String): Properties = rootProject.file(fileName).let { file ->
    Properties().apply { if (file.exists()) load(file.inputStream()) }
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.bksd.brandapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.bksd.brandapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            buildConfigField("String", "BASE_URL", configProperties.getProperty("RELEASE_BASE_URL"))
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            buildConfigField("String", "BASE_URL", configProperties.getProperty("DEBUG_BASE_URL"))
            buildConfigField("String", "API_KEY", configProperties.getProperty("DEBUG_API_KEY"))
            buildConfigField("String", "CLIENT_ID", configProperties.getProperty("DEBUG_CLIENT_ID"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.bundles.core)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.serialization)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.logging)
    implementation(libs.bundles.windowsize)
    implementation(libs.bundles.network)
    implementation(libs.bundles.image)
    implementation(libs.play.services.maps)
    debugImplementation(libs.bundles.compose.tooling)
    testImplementation(libs.bundles.test.unit)
}