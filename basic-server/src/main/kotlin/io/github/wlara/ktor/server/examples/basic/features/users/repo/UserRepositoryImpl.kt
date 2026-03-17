package io.github.wlara.ktor.server.examples.basic.features.users.repo

import io.github.wlara.ktor.server.examples.basic.core.exceptions.HttpConflictException
import io.github.wlara.ktor.server.examples.basic.core.exceptions.HttpNotFoundException
import io.github.wlara.ktor.server.examples.basic.core.extensions.checkEmail
import io.github.wlara.ktor.server.examples.basic.core.extensions.checkName
import io.github.wlara.ktor.server.examples.basic.core.extensions.debug
import io.github.wlara.ktor.server.examples.basic.core.extensions.decodeFromResource
import io.github.wlara.ktor.server.examples.basic.core.extensions.getValue
import io.github.wlara.ktor.server.examples.basic.core.extensions.logger
import io.github.wlara.ktor.server.examples.basic.features.users.dto.CreateUserRequest
import io.github.wlara.ktor.server.examples.basic.features.users.dto.UpdateUserRequest
import io.github.wlara.ktor.server.examples.basic.features.users.dto.UserResponse
import io.ktor.server.config.ApplicationConfig
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Singleton
import java.util.UUID
import kotlin.time.Clock

@Singleton
class UserRepositoryImpl(
    json: Json,
    config: ApplicationConfig
) : UserRepository {

    private val logger by logger()
    private val users = json.decodeFromResource<MutableList<UserResponse>>("/users.json")
    private val maxNameLength = config.getValue<Int>("users.maxNameLength")
    private val maxEmailLength = config.getValue<Int>("users.maxEmailLength")

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

        val name = request.name.trim()
        name.checkName(maxLength = maxNameLength)

        val email = request.email.trim()
        email.checkEmail(maxLength = maxEmailLength)
        email.checkConflict()

        return UserResponse(
            id = UUID.randomUUID(),
            name = name,
            email = email,
            createdAt = Clock.System.now(),
        ).also {
            users.add(it)
        }
    }

    override suspend fun update(id: UUID, request: UpdateUserRequest): UserResponse {
        logger.debug { "update: id=$id, request=$request" }

        val name = request.name?.trim()
        name?.checkName(maxLength = maxNameLength)

        val email = request.email?.trim()
        email?.checkEmail(maxLength = maxEmailLength)
        email?.checkConflict()

        val index = indexOf(id)
        val user = users[index]
        return user.copy(
            name = name ?: user.name,
            email = email ?: user.email
        ).also {
            users[index] = it
        }
    }

    override suspend fun delete(id: UUID) {
        logger.debug { "delete: id=$id" }
        users.removeAt(indexOf(id))
    }

    private fun indexOf(id: UUID): Int {
        val index = users.indexOfFirst { it.id == id }
        if (index == -1) throw HttpNotFoundException("User with id $id not found")
        return index
    }

    private fun String.checkConflict() {
        if (users.any { it.email.equals(this, ignoreCase = true) }) {
            throw HttpConflictException("User with email $this already exists")
        }
    }
}
