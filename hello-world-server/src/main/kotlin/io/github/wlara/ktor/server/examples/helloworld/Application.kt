package io.github.wlara.ktor.server.examples.helloworld

import io.ktor.server.application.Application
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import java.time.LocalDateTime

fun main() {
    embeddedServer(
        factory = CIO,
        port = 8080,
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    routing {
        get("/api/v1/hello") {
            call.respondText("Hello, World! It's ${LocalDateTime.now()}")
        }
    }
}
