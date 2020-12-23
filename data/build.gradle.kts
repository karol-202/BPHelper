import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.KOTLIN_JVM)
    id(Plugins.KOTLIN_SERIALIZATION)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
            freeCompilerArgs = listOf(OptIn.EXPERIMENTAL_COROUTINES_API,
                                      OptIn.FLOW_PREVIEW,
                                      OptIn.EXPERIMENTAL_TIME)
        }
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Deps.KOTLIN_STDLIB)

    implementation(Deps.KOTLIN_COROUTINES_CORE)
    implementation(Deps.KOTLIN_SERIALIZATION_JSON)

    implementation(Deps.KOIN_CORE)
}
