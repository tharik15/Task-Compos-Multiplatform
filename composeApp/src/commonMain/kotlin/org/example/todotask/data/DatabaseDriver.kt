package org.example.todotask.data

import app.cash.sqldelight.db.SqlDriver

interface DatabaseDriver {
    fun createDriver(): SqlDriver
}