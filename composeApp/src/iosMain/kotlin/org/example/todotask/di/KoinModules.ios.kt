package org.example.todotask.di

import app.cash.sqldelight.db.SqlDriver
import org.example.todotask.data.DatabaseDriver
import org.example.todotask.db.IosDatabase
import org.example.todotask.db.createDataBaseDriver
import org.koin.dsl.module

actual fun sqlDelightPlatformModule() = module {
    single<DatabaseDriver> { IosDatabase() }
}