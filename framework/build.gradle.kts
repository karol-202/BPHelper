plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_PARCELIZE)
    id(Plugins.KOTLIN_KAPT)
    id(Plugins.KOTLIN_SERIALIZATION)
}

android {
    compileSdkVersion(AndroidConf.SDK_COMPILE)

    defaultConfig {
        minSdkVersion(AndroidConf.SDK_MIN)
        targetSdkVersion(AndroidConf.SDK_TARGET)
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
        freeCompilerArgs = listOf(OptIn.EXPERIMENTAL_COROUTINES_API)
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":res"))

    implementation(Deps.KOTLIN_STDLIB)

    implementation(Deps.KOTLIN_SERIALIZATION_JSON)

    implementation(Deps.ANDROID_PREFERENCE)

    implementation(Deps.ANDROID_ROOM_KTX)
    kapt(Deps.ANDROID_ROOM_COMPILER)

    implementation(Deps.FIREBASE_CORE)
    implementation(Deps.FIREBASE_CRASHLYTICS)

    implementation(Deps.KOIN_VIEWMODEL)

    implementation(Deps.FLOWPREFERENCES)
}
