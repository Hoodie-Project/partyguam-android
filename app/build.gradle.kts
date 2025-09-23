import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.compose.compiler)

    id("com.google.gms.google-services")
    id("com.google.android.gms.oss-licenses-plugin")
    id("com.google.firebase.crashlytics")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.party.guam"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.party.guham2"
        minSdk = 28
        targetSdk = 35
        versionCode = 2
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            /*storeFile = rootProject.file(properties.getProperty("RELEASE_FILE_PATH"))
            storePassword = properties.getProperty("RELEASE_PASSWORD")
            keyAlias = properties.getProperty("RELEASE_ALIAS")
            keyPassword = properties.getProperty("RELEASE_PASSWORD")*/
        }
    }

    buildTypes {
        debug {
            manifestPlaceholders["KAKAO_OAUTH"] = properties.getProperty("KAKAO_OAUTH")
            buildConfigField("String", "GOOGLE_KEY", "\"${properties.getProperty("GOOGLE_KEY")}\"")
            buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"${properties.getProperty("KAKAO_NATIVE_APP_KEY")}\"")
            buildConfigField("String", "KAKAO_OAUTH", "\"${properties.getProperty("KAKAO_OAUTH")}\"")
            resValue("string", "app_name", "[개발]파티구함")
        }
        release {
            manifestPlaceholders += mapOf()
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            manifestPlaceholders["KAKAO_OAUTH"] = properties.getProperty("KAKAO_OAUTH")
            buildConfigField("String", "GOOGLE_KEY", "\"${properties.getProperty("GOOGLE_KEY")}\"")
            buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"${properties.getProperty("KAKAO_NATIVE_APP_KEY")}\"")
            buildConfigField("String", "KAKAO_OAUTH", "\"${properties.getProperty("KAKAO_OAUTH")}\"")
            resValue("string", "app_name", "파티구함")
            signingConfig = signingConfigs.getByName("release")

            firebaseCrashlytics {
                mappingFileUploadEnabled = false
            }
        }
    }
    composeCompiler {
        includeSourceInformation = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":firebase"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))
    implementation(project(":di"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Google Play services
    implementation(libs.google.services)
    implementation(libs.firebase.auth)
    implementation(platform(libs.firebase.bom))
    implementation(libs.play.services.auth)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics.ktx)

    // kakao login
    implementation(libs.v2.user)

    // OpenSource License
    implementation(libs.oss.licenses)
}

tasks.matching { it.name == "uploadCrashlyticsMappingFileRelease" }.configureEach {
    enabled = false
}