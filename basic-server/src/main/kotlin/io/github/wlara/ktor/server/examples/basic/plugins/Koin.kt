package io.github.wlara.ktor.server.examples.basic.plugins

import io.github.wlara.ktor.server.examples.basic.core.di.KoinApp
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.logger.Level
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.koin.plugin.module.dsl.withConfiguration

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger(Level.INFO)
        withConfiguration<KoinApp>()
        properties(mapOf("application" to this@configureKoin))
    }
}
