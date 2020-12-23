import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.KOTLIN_JVM)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf(OptIn.EXPERIMENTAL_TIME)
        }
    }
}

dependencies {
    implementation(Deps.KOTLIN_STDLIB)
    implementation(Deps.KOTLIN_COROUTINES_CORE)
}
