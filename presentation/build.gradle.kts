plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(project(":domain"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.10")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")

    implementation("org.koin:koin-core:2.2.0")
}
