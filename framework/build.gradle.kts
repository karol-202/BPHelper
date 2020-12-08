plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.android.extensions")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.4.10"
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(17)
        targetSdkVersion(30)
    }

    kapt {
        arguments {
            arg("room.incremental", true)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        freeCompilerArgs = listOf("-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":res"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.10")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")

    implementation("androidx.preference:preference:1.1.1")

    implementation("androidx.room:room-ktx:2.2.5")
    kapt("androidx.room:room-compiler:2.2.5")

    implementation("org.koin:koin-androidx-viewmodel:2.2.0")

    implementation("com.github.tfcporciuncula:flow-preferences:1.3.3")
}
