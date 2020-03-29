/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

pluginManagement {
    repositories {
        mavenLocal()

        jcenter()
        google()

        maven { url = uri("https://dl.bintray.com/kotlin/kotlin") }
        maven { url = uri("https://kotlin.bintray.com/kotlinx") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://dl.bintray.com/icerockdev/plugins") }

        maven { url = uri("https://dl.bintray.com/icerockdev/plugins-dev") }
    }
    resolutionStrategy.eachPlugin {
        val module = Deps.plugins[requested.id.id] ?: return@eachPlugin

        useModule(module)
    }
}

enableFeaturePreview("GRADLE_METADATA")

val properties = startParameter.projectProperties

val pluginPublish: Boolean = properties.containsKey("pluginPublish")
val corePublish: Boolean = properties.containsKey("corePublish")
val additionsPublish: Boolean = properties.containsKey("additionsPublish")

include(":kotlin-common-plugin")
include(":kotlin-plugin")
include(":kotlin-native-plugin")
include(":gradle-plugin")

if (!pluginPublish) {
    include(":widgets")

    if(!corePublish) {
        include(":widgets-flat")
        include(":widgets-sms")
        include(":widgets-bottomsheet")
        include(":widgets-collection")
        include(":widgets-datetime-picker")
        include(":widgets-image-network")

        if (!additionsPublish) {
            include(":sample:android-app")
            include(":sample:mpp-library")
        }
    }
}
