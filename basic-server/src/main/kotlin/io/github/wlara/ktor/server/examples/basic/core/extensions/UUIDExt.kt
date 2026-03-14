package io.github.wlara.ktor.server.examples.basic.core.extensions

import io.ktor.server.plugins.BadRequestException
import java.util.UUID

fun String.toUUID(): UUID =
    try {
        UUID.fromString(this)
    } catch (e: IllegalArgumentException) {
        throw BadRequestException("Malformed UUID: $this", e)
    }
