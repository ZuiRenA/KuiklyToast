plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.tencent.kuiklybase.toast.android"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
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
    implementation("com.tencent.kuikly-open:core-render-android:${Version.getKuiklyVersion()}")
}
