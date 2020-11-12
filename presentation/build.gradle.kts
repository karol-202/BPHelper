plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(project(":domain"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.10")
}
