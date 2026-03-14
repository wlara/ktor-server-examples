package io.github.wlara.ktor.server.examples.basic.features.users.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    val name: String? = null,
    val email: String? = null
)
