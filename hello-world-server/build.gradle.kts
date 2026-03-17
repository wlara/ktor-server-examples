plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
}

group = "io.github.wlara.ktor.server.examples.helloworld"
version = "1.0.0"

application {
    mainClass = "io.github.wlara.ktor.server.examples.helloworld.ApplicationKt"
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(platform(libs.ktor.bom))
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cio)
    implementation(libs.logback.classic)

    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.ktor.server.test.host)
}

