package io.github.wlara.ktor.server.examples.basic.features.users.repo

import io.github.wlara.ktor.server.examples.basic.core.extensions.debug
import io.github.wlara.ktor.server.examples.basic.core.extensions.decodeFromResource
import io.github.wlara.ktor.server.examples.basic.features.users.dto.CreateUserRequest
import io.github.wlara.ktor.server.examples.basic.features.users.dto.UpdateUserRequest
import io.github.wlara.ktor.server.examples.basic.features.users.dto.UserResponse
import io.ktor.server.plugins.NotFoundException
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import java.util.UUID
import kotlin.time.Clock

class UserRepositoryImpl(
    json: Json,
    private val logger: Logger
) : UserRepository {

    private val users: MutableList<UserResponse> = json.decodeFromResource("/users.json")

    override suspend fun getAll(): List<UserResponse> {
        logger.debug { "getAll" }
        return users.toList()
    }

    override suspend fun getById(id: UUID): UserResponse {
        logger.debug { "getById: id=$id" }
        return users[indexOf(id)]
    }

    override suspend fun create(request: CreateUserRequest): UserResponse {
        logger.debug { "create: request=$request" }
        val user = userResponseOf(request.name, request.email)
        users.add(user)
        return user
    }

    override suspend fun update(id: UUID, request: UpdateUserRequest): UserResponse {
        logger.debug { "update: id=$id, request=$request" }
        val index = indexOf(id)
        val existing = users[index]
        val user = existing.copy(
            name = request.name ?: existing.name,
            email = request.email ?: existing.email
        )
        users[index] = user
        return user
    }

    override suspend fun delete(id: UUID) {
        logger.debug { "delete: id=$id" }
        users.removeAt(indexOf(id))
    }

    private fun userResponseOf(name: String, email: String): UserResponse =
        UserResponse(
            id = UUID.randomUUID(),
            name = name,
            email = email,
            createdAt = Clock.System.now(),
        )

    private fun indexOf(id: UUID): Int {
        val index = users.indexOfFirst { it.id == id }
        if (index == -1) throw NotFoundException("User with id $id not found")
        return index
    }
}
