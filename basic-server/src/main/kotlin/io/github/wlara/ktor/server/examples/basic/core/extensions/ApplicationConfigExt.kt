package io.github.wlara.ktor.server.examples.basic.core.extensions

import io.ktor.server.config.ApplicationConfig
import io.ktor.server.config.getAs

inline fun <reified E> ApplicationConfig.getValueOrNull(path: String): E? =
    this.propertyOrNull(path)?.getAs<E>()

inline fun <reified E> ApplicationConfig.getValue(path: String): E =
    requireNotNull(getValueOrNull(path)) {
        "Config property not found: $path"
    }

inline fun <reified E> ApplicationConfig.getValue(path: String, defaultValue: E): E =
    getValueOrNull(path) ?: defaultValue
