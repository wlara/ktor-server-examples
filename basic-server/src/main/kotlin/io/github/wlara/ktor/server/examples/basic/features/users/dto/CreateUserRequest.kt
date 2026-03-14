package io.github.wlara.ktor.server.examples.basic.features.users.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest(
    val name: String,
    val email: String
)
