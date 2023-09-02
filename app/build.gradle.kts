plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        maven(url = "https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("org.jlleitschuh.gradle:ktlint-gradle:11.5.1")
    }
}

apply(plugin = "org.jlleitschuh.gradle.ktlint") // Version should be inherited from parent

android {
    namespace = "com.example.foodrecipeapp"
    @Suppress("MagicNumber")
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.foodrecipeapp"
        @Suppress("MagicNumber")
        minSdk = 24
        @Suppress("MagicNumber")
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            // These values are defined only for the release build, which
            // is typically used for full builds and continuous builds.
            buildConfigField("String", "FOOD_RECIPE_BASE_URL", "\"${property("BASE_URL")}\"")
            buildConfigField("String", "FOOD_RECIPE_API_KEY", "\"${property("API_KEY")}\"")
            buildConfigField("String", "BASE_URL_RECIPES", "\"${property("BASE_URL_RECIPES")}\"")
            buildConfigField(
                "String",
                "BASE_URL_IMAGE_INGREDIENT",
                "\"${property("BASE_URL_IMAGE_INGREDIENT")}\""
            )
            buildConfigField(
                "String",
                "BASE_URL_IMAGE_EQUIPMENT",
                "\"${property("BASE_URL_IMAGE_EQUIPMENT")}\""
            )

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            // Use static values for incremental builds to ensure that
            // resource files and BuildConfig aren't rebuilt with each run.
            // If these rebuild dynamically, they can interfere with
            // Apply Changes as well as Gradle UP-TO-DATE checks.
            buildConfigField("String", "FOOD_RECIPE_BASE_URL", "\"${property("BASE_URL")}\"")
            buildConfigField("String", "FOOD_RECIPE_API_KEY", "\"${property("API_KEY")}\"")
            buildConfigField("String", "BASE_URL_RECIPES", "\"${property("BASE_URL_RECIPES")}\"")
            buildConfigField(
                "String",
                "BASE_URL_IMAGE_INGREDIENT",
                "\"${property("BASE_URL_IMAGE_INGREDIENT")}\""
            )
            buildConfigField(
                "String",
                "BASE_URL_IMAGE_EQUIPMENT",
                "\"${property("BASE_URL_IMAGE_EQUIPMENT")}\""
            )
        }
    }
    buildFeatures {
        viewBinding {
            enable = true
        }
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildToolsVersion = "33.0.2"
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    // For control over item selection of both touch and mouse driven selection
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation("androidx.cardview:cardview:1.0.0")
    // https://mvnrepository.com/artifact/androidx.palette/palette
    implementation("androidx.palette:palette-ktx:1.0.0")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.27")
}
