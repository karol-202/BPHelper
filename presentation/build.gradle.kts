import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
        }
    }
}

dependencies {
    implementation(project(":interactors"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.10")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")

    implementation("org.koin:koin-core:2.2.0")
}
