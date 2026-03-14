package io.github.wlara.ktor.server.examples.basic.features.users

import io.github.wlara.ktor.server.examples.basic.core.extensions.id
import io.github.wlara.ktor.server.examples.basic.features.users.dto.CreateUserRequest
import io.github.wlara.ktor.server.examples.basic.features.users.dto.UpdateUserRequest
import io.github.wlara.ktor.server.examples.basic.features.users.repo.UserRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val repository: UserRepository by inject()

    route("/api/v1/users") {
        get {
            call.respond(repository.getAll())
        }

        post {
            val request = call.receive<CreateUserRequest>()
            call.respond(HttpStatusCode.Created, repository.create(request))
        }

        route("/{id}") {
            get {
                call.respond(repository.getById(call.id))
            }

            patch {
                val request = call.receive<UpdateUserRequest>()
                call.respond(repository.update(call.id, request))
            }

            delete {
                repository.delete(call.id)
                call.respond(HttpStatusCode.NoContent)
            }
        }
    }
}
