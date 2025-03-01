package org.example.todotask.di

import app.cash.sqldelight.db.SqlDriver
import org.example.todotask.data.UserRepository
import org.example.todotask.data.UserRepositoryImpl
import org.example.todotask.repositories.DataStoreRepository
import org.example.todotask.repositories.ToDoRepository
import org.example.todotask.viewmodel.SharedViewModel
import org.example.todotask.viewmodel.UserViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


fun provideDatabase(driver: SqlDriver): org.example.todotask.db.TodoDatabase {
    return org.example.todotask.db.TodoDatabase(driver)
}

val appModule = module {

//    singleOf(::UserRepositoryImpl){ bind<UserRepository>()}
//    factory { getPlatform(this) }
//    viewModel { UserViewModel(get(),get()) }

//    single { platformModule() }
    // Provide the DAO instance
//    single { get<TodoDatabase>().todoDao() }

    // Provide the repositories
//    single { ToDoRepository(get()) }
//    viewModel { SharedViewModel(get()) }
}

val provideDataStoreRepository = module {
    singleOf(::DataStoreRepository)
}

val provideDrive = module {
    singleOf(::provideDatabase)
}

val provideUseCaseModule = module {
    singleOf(::ToDoRepository)
    singleOf(::UserRepositoryImpl).bind(UserRepository::class)
}

val provideViewModelModule = module {
    viewModelOf(::SharedViewModel)
    viewModelOf(::UserViewModel)
}

expect fun sqlDelightPlatformModule(): Module

expect fun dataStorePlatformModule():Module