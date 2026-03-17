package io.github.wlara.ktor.server.examples.basic.features.products.repo

import io.github.wlara.ktor.server.examples.basic.core.exceptions.HttpNotFoundException
import io.github.wlara.ktor.server.examples.basic.core.extensions.debug
import io.github.wlara.ktor.server.examples.basic.core.extensions.decodeFromResource
import io.github.wlara.ktor.server.examples.basic.core.extensions.getValue
import io.github.wlara.ktor.server.examples.basic.core.extensions.logger
import io.github.wlara.ktor.server.examples.basic.features.products.dto.ProductResponse
import io.ktor.server.config.ApplicationConfig
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Singleton
import java.util.UUID

@Singleton
class ProductRepositoryImpl(
    json: Json,
    config: ApplicationConfig
) : ProductRepository {

    private val logger by logger()
    private val products = json.decodeFromResource<List<ProductResponse>>("/products.json")
    private val sortNewestFirst = config.getValue<Boolean>("products.sortNewestFirst")

    override suspend fun getAll(query: String?): List<ProductResponse> {
        logger.debug { "getAll: query=\"$query\"" }

        val filteredProducts = if (query.isNullOrBlank()) {
            products
        } else {
            val words = query.split(" ").filter { it.isNotBlank() }
            products.filter { product ->
                words.any { word ->
                    product.name.contains(word, ignoreCase = true) ||
                    product.description.contains(word, ignoreCase = true)
                }
            }
        }

        return if (sortNewestFirst) {
            filteredProducts.sortedByDescending { it.createdAt }
        } else {
            filteredProducts
        }
    }

    override suspend fun getById(id: UUID): ProductResponse {
        logger.debug { "getById: id=$id" }
        return products.firstOrNull { it.id == id }
            ?: throw HttpNotFoundException("Product with id $id not found")
    }
}
