package org.example.todotask

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.scope.Scope

interface Platform {
    val name: String
}

expect fun getPlatform(scope: Scope): Platform

// commonMain
expect fun showToast(message: String, context: Any)

expect fun createDataStore(scope: Scope):DataStore<Preferences>



