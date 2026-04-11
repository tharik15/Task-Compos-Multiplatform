package org.example.todotask.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.example.todotask.data.DatabaseDriver

class IosDatabase : DatabaseDriver {
    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = TodoDatabase.Schema,
            name = "TodoDatabase.db"
        )
    }
}

