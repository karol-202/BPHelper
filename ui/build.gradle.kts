plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.android.extensions")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(30)


    defaultConfig {
        minSdkVersion(17)
        targetSdkVersion(30)

        /*versionCode = 1
        versionName = "1.0"

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
                arg("room.incremental", true)
            }
        }*/
    }

    /*buildTypes["release"].apply {
        isMinifyEnabled = false
        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }



    kotlinOptions {
        kotlinOptions.freeCompilerArgs += listOf("-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                                                 "-Xopt-in=kotlinx.coroutines.FlowPreview",
                                                 "-Xopt-in=kotlinx.serialization.UnstableDefault")
    }*/

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

androidExtensions {
    isExperimental = true
}

dependencies {
    implementation(project(":presentation"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.10")

    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.fragment:fragment-ktx:1.2.5")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.recyclerview:recyclerview:1.1.0")
    implementation("androidx.gridlayout:gridlayout:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.preference:preference:1.1.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")

    implementation("androidx.navigation:navigation-fragment-ktx:2.3.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.1")

    implementation("com.google.android.material:material:1.2.1")

    implementation("org.koin:koin-androidx-viewmodel:2.2.0")
}
