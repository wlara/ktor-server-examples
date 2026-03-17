package io.github.wlara.ktor.server.examples.basic.plugins

import io.github.wlara.ktor.server.examples.basic.core.exceptions.HttpStatusException
import io.github.wlara.ktor.server.examples.basic.core.extensions.deepestCause
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

fun Application.configureStatusPages() {
    install(StatusPages) {

        exception<HttpStatusException> { call, cause ->
            call.respondError(cause.statusCode, cause)
        }

        exception<Throwable> { call, cause ->
            this@configureStatusPages.log.error("InternalServerError", cause)
            call.respondError(HttpStatusCode.InternalServerError, cause)
        }
    }
}

private suspend fun ApplicationCall.respondError(
    statusCode: HttpStatusCode,
    cause: Throwable
) = this.respond(
    status = statusCode,
    message = mapOf(
        "code" to statusCode.value.toString(),
        "description" to statusCode.description,
        "cause" to cause.deepestCause().let { it.message ?: it.toString() }
    )
)
