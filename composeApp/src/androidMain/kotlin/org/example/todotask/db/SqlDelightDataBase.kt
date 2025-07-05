package org.example.todotask.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.koin.core.scope.Scope

fun createDataBaseDriver(scope: Scope): SqlDriver {
    return AndroidSqliteDriver(
        schema = TodoDatabase.Schema,
        context = scope.get(),
        name = "TodoDatabase.db"
    )
}