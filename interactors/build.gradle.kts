plugins {
    id("org.jetbrains.kotlin.jvm")
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
            freeCompilerArgs = listOf("-Xopt-in=kotlin.time.ExperimentalTime",
                                      "-Xjvm-default=all")
            // Using -Xjvm-default=all here because of bug related to
            // wrong code generation when using inline classes with default methods
        }
    }
}

dependencies {
    api(project(":domain"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.10")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")

    implementation("org.koin:koin-core:2.2.0")
}
