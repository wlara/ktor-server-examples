package io.github.wlara.ktor.server.examples.basic.core.extensions

import io.github.wlara.ktor.server.examples.basic.core.exceptions.HttpBadRequestException
import io.ktor.server.plugins.BadRequestException
import java.util.UUID

fun String.toUUID(): UUID =
    try {
        UUID.fromString(this)
    } catch (_: IllegalArgumentException) {
        throw HttpBadRequestException("Malformed UUID: $this")
    }
