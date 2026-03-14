package io.github.wlara.ktor.server.examples.basic.plugins

import io.github.wlara.ktor.server.examples.basic.core.di.appModule
import io.github.wlara.ktor.server.examples.basic.features.products.di.productModule
import io.github.wlara.ktor.server.examples.basic.features.users.di.userModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(
            appModule,
            userModule,
            productModule
        )
    }
}
