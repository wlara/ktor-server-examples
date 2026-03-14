package io.github.wlara.ktor.server.examples.basic.plugins

import io.github.wlara.ktor.server.examples.basic.features.products.productRoutes
import io.github.wlara.ktor.server.examples.basic.features.users.userRoutes
import io.ktor.server.application.Application
import io.ktor.server.http.content.staticResources
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        staticResources(remotePath = "/", basePackage = "static")
        userRoutes()
        productRoutes()
    }
}
