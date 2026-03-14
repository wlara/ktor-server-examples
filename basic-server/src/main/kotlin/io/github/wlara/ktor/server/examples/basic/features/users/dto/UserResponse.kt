package io.github.wlara.ktor.server.examples.basic.features.users.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.time.Instant

@Serializable
data class UserResponse(
    @Contextual val id: UUID,
    val name: String,
    val email: String,
    @Contextual val createdAt: Instant
)
