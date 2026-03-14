package io.github.wlara.ktor.server.examples.basic.features.users.repo

import io.github.wlara.ktor.server.examples.basic.features.users.dto.CreateUserRequest
import io.github.wlara.ktor.server.examples.basic.features.users.dto.UpdateUserRequest
import io.github.wlara.ktor.server.examples.basic.features.users.dto.UserResponse
import java.util.UUID

interface UserRepository {
    suspend fun getAll(): List<UserResponse>
    suspend fun getById(id: UUID): UserResponse
    suspend fun create(request: CreateUserRequest): UserResponse
    suspend fun update(id: UUID, request: UpdateUserRequest): UserResponse
    suspend fun delete(id: UUID)
}
