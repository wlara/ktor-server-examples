package io.github.wlara.ktor.server.examples.basic.features.products.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.time.Instant

@Serializable
data class ProductResponse(
    @Contextual val id: UUID,
    val name: String,
    val description: String,
    val price: Double,
    @Contextual val createdAt: Instant
)
