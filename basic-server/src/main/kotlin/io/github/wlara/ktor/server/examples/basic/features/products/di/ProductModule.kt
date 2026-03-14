package io.github.wlara.ktor.server.examples.basic.features.products.di

import io.github.wlara.ktor.server.examples.basic.features.products.repo.ProductRepository
import io.github.wlara.ktor.server.examples.basic.features.products.repo.ProductRepositoryImpl
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val productModule = module {
    single<ProductRepository> {
        ProductRepositoryImpl(
            json = get(),
            logger = get { parametersOf(ProductRepositoryImpl::class) }
        )
    }
}
