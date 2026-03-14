package io.github.wlara.ktor.server.examples.basic.features.users.di

import io.github.wlara.ktor.server.examples.basic.features.users.repo.UserRepository
import io.github.wlara.ktor.server.examples.basic.features.users.repo.UserRepositoryImpl
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val userModule = module {
    single<UserRepository> {
        UserRepositoryImpl(
            json = get(),
            logger = get { parametersOf(UserRepositoryImpl::class) }
        )
    }
}
