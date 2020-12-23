plugins {
    id(Plugins.KOTLIN_JVM) version Versions.KOTLIN apply false
    id(Plugins.KOTLIN_ANDROID) version Versions.KOTLIN apply false
    id(Plugins.KOTLIN_ANDROID_EXTENSIONS) version Versions.KOTLIN apply false

    id(Plugins.KOTLIN_SERIALIZATION) version Versions.KOTLIN apply false
    id(Plugins.KOTLIN_KAPT) version Versions.KOTLIN apply false

    id(Plugins.ANDROID_APP) version Versions.ANDROID_GRADLE_PLUGIN apply false
    id(Plugins.ANDROID_LIBRARY) version Versions.ANDROID_GRADLE_PLUGIN apply false

    id(Plugins.ANDROID_NAVIGATION) version Versions.ANDROID_NAVIGATION apply false

    id(Plugins.GOOGLE_SERVICES) version Versions.GOOGLE_SERVICES apply false
    id(Plugins.FIREBASE_CRASHLYTICS) version Versions.FIREBASE_CRASHLYTICS_GRADLE_PLUGIN apply false
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
    }
}

/*task clean(type: Delete) {
    delete rootProject.buildDir
}*/
