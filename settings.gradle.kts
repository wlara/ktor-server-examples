@file:Suppress("UnstableApiUsage")

rootProject.name = "ktor-server-examples"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include("hello-world-server")
include("basic-server")
