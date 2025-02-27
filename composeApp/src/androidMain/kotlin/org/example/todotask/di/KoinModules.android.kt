package org.example.todotask.di

import org.example.todotask.data.TodoDatabase
import org.example.todotask.db.getDatabaseBuilder
import org.koin.dsl.module

actual fun platformModule() = module {
    single<TodoDatabase> { getDatabaseBuilder(get()) }
}