package io.github.wlara.ktor.server.examples.basic.core.di

import io.github.wlara.ktor.server.examples.basic.core.serializers.UUIDSerializer
import io.ktor.server.application.Application
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.koin.core.module.Module
import org.koin.dsl.module
import org.slf4j.LoggerFactory
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
val Application.appModule: Module
    get() = module {
        single { this@appModule }
        single { this@appModule.environment }
        factory { LoggerFactory.getLogger(it.get<KClass<*>>().java.simpleName) }
        single {
            Json {
                ignoreUnknownKeys = true
                explicitNulls = false
                isLenient = true
                serializersModule = SerializersModule {
                    contextual(Instant::class, Instant.serializer())
                    contextual(UUID::class, UUIDSerializer)
                }
            }
        }
    }
