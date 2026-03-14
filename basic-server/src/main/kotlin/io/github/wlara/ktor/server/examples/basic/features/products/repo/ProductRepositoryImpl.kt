package io.github.wlara.ktor.server.examples.basic.features.products.repo

import io.github.wlara.ktor.server.examples.basic.core.extensions.debug
import io.github.wlara.ktor.server.examples.basic.core.extensions.decodeFromResource
import io.github.wlara.ktor.server.examples.basic.features.products.dto.ProductResponse
import io.ktor.server.plugins.NotFoundException
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import java.util.UUID

class ProductRepositoryImpl(
    json: Json,
    private val logger: Logger
) : ProductRepository {

    private val products: List<ProductResponse> = json.decodeFromResource("/products.json")

    override suspend fun getAll(query: String?): List<ProductResponse> {
        logger.debug { "getAll: query=\"$query\"" }
        return if (query == null) {
            products.toList()
        } else {
            val words = query.split(" ")
            products.filter { product ->
                words.any { word ->
                    product.name.contains(word, ignoreCase = true) ||
                    product.description.contains(word, ignoreCase = true)
                }
            }
        }
    }

    override suspend fun getById(id: UUID): ProductResponse {
        logger.debug { "getById: id=$id" }
        return products.firstOrNull { it.id == id }
            ?: throw NotFoundException("Product with id $id not found")
    }
}
