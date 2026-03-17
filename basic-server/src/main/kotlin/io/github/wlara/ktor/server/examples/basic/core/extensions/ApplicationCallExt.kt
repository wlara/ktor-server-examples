package io.github.wlara.ktor.server.examples.basic.core.extensions

import io.github.wlara.ktor.server.examples.basic.core.exceptions.HttpBadRequestException
import io.ktor.server.application.ApplicationCall
import java.util.UUID

val ApplicationCall.id: UUID
    get() = parameters["id"]?.toUUID()
        ?: throw HttpBadRequestException("Missing parameter: id")
