package org.example.todotask.di

import org.example.todotask.createDataStore
import org.example.todotask.data.DatabaseDriver
import org.example.todotask.db.AndroidDatabase
import org.koin.dsl.module

actual fun sqlDelightPlatformModule() = module {
    single<DatabaseDriver> { AndroidDatabase(get()) }
}

actual fun dataStorePlatformModule() = module {
    single { createDataStore(this)  }
}