package org.example.todotask

import androidx.compose.runtime.Composable
import app.cash.sqldelight.db.SqlDriver
import org.koin.core.scope.Scope

interface Platform {
    val name: String
}

expect fun getPlatform(scope: Scope): Platform

// commonMain
expect fun showToast(message: String, context: Any)



