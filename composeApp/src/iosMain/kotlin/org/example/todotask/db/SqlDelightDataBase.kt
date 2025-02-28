package org.example.todotask.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

fun createDataBaseDriver(): SqlDriver {
    return NativeSqliteDriver(
        schema = TodoDatabase.Schema,
        name = "TodoDatabase.db"
    )
}