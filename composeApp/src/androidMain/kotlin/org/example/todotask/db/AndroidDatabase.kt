package org.example.todotask.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.example.todotask.data.DatabaseDriver

class AndroidDatabase(private val context: Context) : DatabaseDriver {
    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = TodoDatabase.Schema,
            context = context,
            name = "TodoDatabase.db"
        )
    }
}