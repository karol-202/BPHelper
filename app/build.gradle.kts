plugins {
    id(Plugins.ANDROID_APP)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.GOOGLE_SERVICES)
    id(Plugins.FIREBASE_CRASHLYTICS)
}

android {
    compileSdkVersion(AndroidConf.SDK_COMPILE)

    defaultConfig {
        minSdkVersion(AndroidConf.SDK_MIN)
        targetSdkVersion(AndroidConf.SDK_TARGET)

        applicationId = "pl.karol202.bphelper"
        versionCode = 6
        versionName = "1.0.5"
    }

    buildTypes["release"].apply {
        isMinifyEnabled = true
        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf(OptIn.EXPERIMENTAL_TIME,
                                  OptIn.EXPERIMENTAL_COROUTINES_API)
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":framework"))
    implementation(project(":interactors"))
    implementation(project(":presentation"))
    implementation(project(":ui"))

    implementation(Deps.KOIN_VIEWMODEL)
}
