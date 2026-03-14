package io.github.wlara.ktor.server.examples.basic.plugins

import io.github.wlara.ktor.server.examples.basic.core.extensions.deepestCause
import io.github.wlara.ktor.server.examples.basic.core.extensions.error
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.inject
import org.slf4j.Logger

fun Application.configureStatusPages() {
    val logger: Logger by inject { parametersOf(this::class) }

    install(StatusPages) {

        exception<NotFoundException> { call, cause ->
            call.respondError(HttpStatusCode.NotFound, cause)
        }

        exception<IllegalStateException> { call, cause ->
            call.respondError(HttpStatusCode.Conflict, cause)
        }

        exception<BadRequestException> { call, cause ->
            call.respondError(HttpStatusCode.BadRequest, cause)
        }
        exception<Throwable> { call, cause ->
            logger.error(cause) { "InternalServerError" }
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
