import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

val generatedDocsDir = layout.buildDirectory.dir("generated/assest/rootDocs")

android {
    namespace = "cvc.dashingdog.tydgenoot"
    compileSdk = 36

    defaultConfig {
        applicationId = "cvc.dashingdog.tydgenoot"
        minSdk = 21
        targetSdk = 36
        versionCode = 2
        versionName = "0.2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        compose = true
    }

    sourceSets {
        getByName("main") {
            assets.directories.add(generatedDocsDir.get().asFile.toString())
        }
    }
}

val copyRootDocsToAssets = tasks.register<Copy>("copyRootDocsToAssets") {
    from(rootProject.layout.projectDirectory) {
        include("ABOUT.md", "CREDITS.md", "LICENSE")
    }
    into(generatedDocsDir)
}

tasks.named("preBuild") {
    dependsOn(copyRootDocsToAssets)
}

androidComponents {
    onVariants { variant ->
        variant.outputs.forEach { output ->
            val timestamp = SimpleDateFormat("yyyyMMdd-HHmm").format(Date())
            val versionName = android.defaultConfig.versionName
            output.outputFileName.set("${rootProject.name}-$versionName-$timestamp-${variant.name}.apk")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.markdown.renderer.m3)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}