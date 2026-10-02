import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

// AdMob IDs. Debug builds ALWAYS use Google's test ads: showing real ads to yourself breaks AdMob policy.
// For release, put your real IDs in local.properties (not committed) or gradle.properties:
//   ADMOB_APP_ID=ca-app-pub-XXXXXXXXXXXXXXXX~YYYYYYYYYY
//   ADMOB_INTERSTITIAL_ID=ca-app-pub-XXXXXXXXXXXXXXXX/ZZZZZZZZZZ
//   ADMOB_REWARDED_ID=ca-app-pub-XXXXXXXXXXXXXXXX/WWWWWWWWWW
// Until they are set, release builds also show test ads (and earn nothing).
val admobTestAppId = "ca-app-pub-3940256099942544~3347511713"
val admobTestInterstitialId = "ca-app-pub-3940256099942544/1033173712"
val admobTestRewardedId = "ca-app-pub-3940256099942544/5224354917"
val localProps = Properties().apply {
    rootProject.file("local.properties").takeIf { it.exists() }?.inputStream()?.use { load(it) }
}
fun setting(name: String): String? = (findProperty(name) as String?) ?: localProps.getProperty(name)
// Upload key for Google Play, kept out of git. If keystore/keystore.properties is missing, release builds stay unsigned.
val signingProps = Properties().apply {
    rootProject.file("keystore/keystore.properties").takeIf { it.exists() }?.inputStream()?.use { load(it) }
}
val releaseAdmobAppId = setting("ADMOB_APP_ID")
val releaseInterstitialId = setting("ADMOB_INTERSTITIAL_ID")
val releaseRewardedId = setting("ADMOB_REWARDED_ID")

android {
    namespace = "com.example.quranfacts"
    compileSdk = 36

    defaultConfig {
        // The Play Store identity: permanent once published. (The code namespace above stays com.example.quranfacts.)
        applicationId = "com.mymax.quranscience"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["admobAppId"] = admobTestAppId
        buildConfigField("String", "ADMOB_INTERSTITIAL_ID", "\"$admobTestInterstitialId\"")
        buildConfigField("String", "ADMOB_REWARDED_ID", "\"$admobTestRewardedId\"")
    }

    signingConfigs {
        if (signingProps.isNotEmpty()) {
            create("release") {
                storeFile = rootProject.file("keystore/${signingProps.getProperty("storeFile")}")
                storePassword = signingProps.getProperty("storePassword")
                keyAlias = signingProps.getProperty("keyAlias")
                keyPassword = signingProps.getProperty("keyPassword")
            }
        }
    }
    buildTypes {
        release {
            signingConfigs.findByName("release")?.let { signingConfig = it }
            if (releaseAdmobAppId == null || releaseInterstitialId == null || releaseRewardedId == null) {
                logger.warn("Quran Facts: ADMOB_APP_ID / ADMOB_INTERSTITIAL_ID / ADMOB_REWARDED_ID not set, so the release build shows Google test ads.")
            }
            manifestPlaceholders["admobAppId"] = releaseAdmobAppId ?: admobTestAppId
            buildConfigField("String", "ADMOB_INTERSTITIAL_ID", "\"${releaseInterstitialId ?: admobTestInterstitialId}\"")
            buildConfigField("String", "ADMOB_REWARDED_ID", "\"${releaseRewardedId ?: admobTestRewardedId}\"")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    testOptions {
        // The data-integrity tests read assets/facts.json straight from disk, so Gradle must
        // treat it as an input; otherwise a regenerated file could be skipped as "up to date".
        unitTests.all { it.inputs.dir("src/main/assets") }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.datasource.okhttp)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.play.services.ads)
    implementation(libs.billing.ktx)
    implementation(libs.ump)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
