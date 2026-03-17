package io.github.wlara.ktor.server.examples.basic.core.di

import io.github.wlara.ktor.server.examples.basic.core.serializers.UUIDSerializer
import io.ktor.server.application.Application
import io.ktor.server.config.ApplicationConfig
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Property
import org.koin.core.annotation.Singleton
import java.util.UUID
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


@Module
@ComponentScan("io.github.wlara.ktor.server.examples.basic")
@Configuration
class AppModule {

    @Singleton
    fun provideApplicationConfig(
        @Property("application") application: Application
    ): ApplicationConfig = application.environment.config

    @OptIn(ExperimentalTime::class)
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        isLenient = true
        serializersModule = SerializersModule {
            contextual(Instant::class, Instant.serializer())
            contextual(UUID::class, UUIDSerializer)
        }
    }
}
