plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_ANDROID_EXTENSIONS)
    id(Plugins.ANDROID_NAVIGATION)
}

android {
    compileSdkVersion(AndroidConf.SDK_COMPILE)

    defaultConfig {
        minSdkVersion(AndroidConf.SDK_MIN)
        targetSdkVersion(AndroidConf.SDK_TARGET)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf(OptIn.EXPERIMENTAL_TIME)
    }
}

androidExtensions {
    isExperimental = true
}

dependencies {
    implementation(project(":presentation"))
    implementation(project(":res"))

    implementation(Deps.KOTLIN_STDLIB)

    implementation(Deps.ANDROID_CORE_KTX)
    implementation(Deps.ANDROID_FRAGMENT_KTX)
    implementation(Deps.ANDROID_APPCOMPAT)
    implementation(Deps.ANDROID_RECYCLERVIEW)
    implementation(Deps.ANDROID_GRIDLAYOUT)
    implementation(Deps.ANDROID_CONSTRAINTLAYOUT)
    implementation(Deps.ANDROID_PREFERENCE)
    implementation(Deps.ANDROID_VIEWMODEL_KTX)

    implementation(Deps.ANDROID_NAVIGATION_FRAGMENT_KTX)
    implementation(Deps.ANDROID_NAVIGATION_UI_KTX)

    implementation(Deps.ANDROID_MATERIAL)

    implementation(Deps.KOIN_VIEWMODEL)
}
