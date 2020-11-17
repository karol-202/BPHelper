plugins {
    id("org.jetbrains.kotlin.jvm")
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xopt-in=kotlin.time.ExperimentalTime")
        }
    }
}

dependencies {
    api(project(":domain"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.10")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")

    implementation("org.koin:koin-core:2.2.0")
}
