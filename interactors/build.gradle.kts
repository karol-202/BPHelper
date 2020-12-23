import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.KOTLIN_JVM)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
            freeCompilerArgs = listOf(OptIn.EXPERIMENTAL_TIME,
                                      CompilerArgs.DEFAULT_ALL)
            // Using -Xjvm-default=all here because of bug related to
            // wrong code generation when using inline classes with default methods
        }
    }
}

dependencies {
    api(project(":domain"))

    implementation(Deps.KOTLIN_STDLIB)
    implementation(Deps.KOTLIN_COROUTINES_CORE)

    implementation(Deps.KOIN_CORE)
}
