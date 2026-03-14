package io.github.wlara.ktor.server.examples.basic

import io.github.wlara.ktor.server.examples.basic.plugins.configureKoin
import io.github.wlara.ktor.server.examples.basic.plugins.configureMonitoring
import io.github.wlara.ktor.server.examples.basic.plugins.configureRouting
import io.github.wlara.ktor.server.examples.basic.plugins.configureSerialization
import io.github.wlara.ktor.server.examples.basic.plugins.configureStatusPages
import io.ktor.server.application.Application

@Suppress("unused")
fun Application.module() {
    configureKoin()
    configureMonitoring()
    configureStatusPages()
    configureSerialization()
    configureRouting()
}
