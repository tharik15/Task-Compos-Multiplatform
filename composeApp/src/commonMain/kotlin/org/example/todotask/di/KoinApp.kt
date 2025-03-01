package org.example.todotask.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config : KoinAppDeclaration? = null) =
    startKoin { config?.invoke(this)
    modules(sqlDelightPlatformModule(),dataStorePlatformModule(),
        provideDrive,
        provideDataStoreRepository,
        provideUseCaseModule,
        provideViewModelModule)
}