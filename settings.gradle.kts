pluginManagement {
	repositories {
		gradlePluginPortal()
		google()
	}

	resolutionStrategy {
		eachPlugin {
			// Android does not place its plugins into Gradle repo,
			// so their search location must be specified
			// in order to be able to use them with plugins {} dsl (without buildscript block)
			when(requested.id.id)
			{
				"com.android.application", "com.android.library" ->
					useModule("com.android.tools.build:gradle:${requested.version}")
				"androidx.navigation.safeargs.kotlin" ->
					useModule("androidx.navigation:navigation-safe-args-gradle-plugin:${requested.version}")
				"com.google.gms.google-services" ->
					useModule("com.google.gms:google-services:${requested.version}")
				"com.google.firebase.crashlytics" ->
					useModule("com.google.firebase:firebase-crashlytics-gradle:${requested.version}")
			}
		}
	}
}

include(":domain")
include(":data")
include(":framework")
include(":interactors")
include(":presentation")
include(":ui")
include(":res")
include(":app")
