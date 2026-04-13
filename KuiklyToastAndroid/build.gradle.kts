plugins {
    id("com.android.library")
    kotlin("android")
    `maven-publish`
}

val mavenVersion: String = findProperty("mavenVersion") as? String
    ?: findProperty("MAVEN_VERSION") as? String
    ?: "1.0.0"
val groupId: String = findProperty("groupId") as? String
    ?: findProperty("GROUP_ID") as? String
    ?: "com.tencent.kuiklybase"
val mavenRepoUrl: String = findProperty("mavenRepoUrl") as? String
    ?: findProperty("MAVEN_REPO_URL") as? String
    ?: ""
val mavenUsername: String = findProperty("mavenUsername") as? String
    ?: findProperty("MAVEN_USERNAME") as? String
    ?: ""
val mavenPassword: String = findProperty("mavenPassword") as? String
    ?: findProperty("MAVEN_PASSWORD") as? String
    ?: ""

group = groupId
version = mavenVersion

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

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = this@afterEvaluate.group.toString()
                artifactId = "KuiklyToastAndroid"
                version = this@afterEvaluate.version.toString()
            }
        }
        repositories {
            maven {
                url = uri(mavenRepoUrl)
                credentials {
                    username = mavenUsername
                    password = mavenPassword
                }
            }
        }
    }
}
