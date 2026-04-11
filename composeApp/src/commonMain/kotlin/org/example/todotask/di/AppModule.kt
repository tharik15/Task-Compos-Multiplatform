package org.example.todotask.di

import org.example.todotask.repositories.DataStoreRepository
import org.example.todotask.repositories.ToDoRepository
import org.example.todotask.viewmodel.SharedViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val provideDataStoreRepository = module {
    singleOf(::DataStoreRepository)
}

val provideUseCaseModule = module {
    singleOf(::ToDoRepository)
}

val provideViewModelModule = module {
    viewModelOf(::SharedViewModel)
}

expect fun sqlDelightPlatformModule(): Module

expect fun dataStorePlatformModule():Module