package io.github.wlara.ktor.server.examples.basic.features.products.repo

import io.github.wlara.ktor.server.examples.basic.features.products.dto.ProductResponse
import java.util.UUID

interface ProductRepository {
    suspend fun getAll(query: String? = null): List<ProductResponse>
    suspend fun getById(id: UUID): ProductResponse
}
