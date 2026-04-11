package org.example.todotask.di

import org.example.todotask.createDataStore
import org.example.todotask.data.DatabaseDriver
import org.example.todotask.db.DesktopDatabase
import org.koin.dsl.module


actual fun sqlDelightPlatformModule() = module {
    single<DatabaseDriver> { DesktopDatabase() }
}

actual fun dataStorePlatformModule() = module {
    single { createDataStore(this)  }
}