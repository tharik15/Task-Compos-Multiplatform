package org.example.todotask.di

import org.example.todotask.getPlatform
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.koin.dsl.module

fun initKoin(config : KoinAppDeclaration? = null) =
    startKoin { config?.invoke(this)
    modules(platformModule(),
        provideUseCaseModule,provideViewModelModule, )
}