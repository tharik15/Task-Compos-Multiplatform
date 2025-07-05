package org.example.todotask.di

import app.cash.sqldelight.db.SqlDriver
import org.example.todotask.createDataStore
import org.example.todotask.db.createDataBaseDriver
import org.koin.dsl.module

actual fun sqlDelightPlatformModule() = module {
    single<SqlDriver> { createDataBaseDriver(this) }
}

actual fun dataStorePlatformModule() = module {
    single { createDataStore(this)  }
}