package io.github.wlara.ktor.server.examples.basic.core.extensions

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> Json.decodeFromResource(resourceName: String): T {
    val inputStream = object {}.javaClass.getResourceAsStream(resourceName)
        ?: throw IllegalArgumentException("Resource not found: $resourceName")

    return inputStream.use { stream ->
        this.decodeFromStream<T>(stream)
    }
}
