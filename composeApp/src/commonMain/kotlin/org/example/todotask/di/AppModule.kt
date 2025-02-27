package org.example.todotask.di


import androidx.room.Room
import org.example.todotask.Factory
import org.example.todotask.data.TodoDatabase
import org.example.todotask.util.Constants.DATABASE_NAME
import org.example.todotask.data.UserRepository
import org.example.todotask.data.UserRepositoryImpl
import org.example.todotask.getPlatform
import org.example.todotask.repositories.ToDoRepository
import org.example.todotask.viewmodel.SharedViewModel
import org.example.todotask.viewmodel.UserViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

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

val provideUseCaseModule = module {
    singleOf(::ToDoRepository)
    singleOf(::UserRepositoryImpl).bind(UserRepository::class)
}

val provideViewModelModule = module {
    viewModelOf(::SharedViewModel)
    viewModelOf(::UserViewModel)
}

expect fun platformModule(): Module