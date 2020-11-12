plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
}

android {
    compileSdkVersion(30)
}

dependencies {
    implementation(project(":data"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.10")

    implementation("androidx.room:room-runtime:2.2.5")
    kapt("androidx.room:room-compiler:2.2.5")

    implementation("org.koin:koin-androidx-viewmodel:2.2.0")
}
