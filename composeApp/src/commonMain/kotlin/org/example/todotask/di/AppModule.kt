package org.example.todotask.di

import org.example.todotask.data.repository.DataStoreRepositoryImpl
import org.example.todotask.data.repository.ToDoRepositoryImpl
import org.example.todotask.ui.screen.viewmodel.SharedViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val provideDataStoreRepository = module {
    singleOf(::DataStoreRepositoryImpl)
}

val provideUseCaseModule = module {
    singleOf(::ToDoRepositoryImpl)
}

val provideViewModelModule = module {
    viewModelOf(::SharedViewModel)
}

expect fun sqlDelightPlatformModule(): Module

expect fun dataStorePlatformModule():Module