package io.github.wlara.ktor.server.examples.basic.core.extensions

import io.ktor.server.application.ApplicationCall
import io.ktor.server.plugins.MissingRequestParameterException
import java.util.UUID

val ApplicationCall.id: UUID
    get() = parameters["id"]?.toUUID()
        ?: throw MissingRequestParameterException("id")
