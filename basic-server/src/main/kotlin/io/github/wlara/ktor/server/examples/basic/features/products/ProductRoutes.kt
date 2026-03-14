package io.github.wlara.ktor.server.examples.basic.features.products

import io.github.wlara.ktor.server.examples.basic.core.extensions.id
import io.github.wlara.ktor.server.examples.basic.features.products.repo.ProductRepository
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.productRoutes() {
    val repository: ProductRepository by inject()

    route("/api/v1/products") {
        get {
            val query = call.request.queryParameters["query"]
            call.respond(repository.getAll(query))
        }

        route("{id}") {
            get {
                call.respond(repository.getById(call.id))
            }
        }
    }
}
