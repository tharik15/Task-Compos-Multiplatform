package org.example.todotask

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.core.scope.Scope
import java.io.File

actual fun showToast(message: String, context: Any) {
}

actual fun createDataStore(scope: Scope): DataStore<Preferences> {
    val dataStoreFile = File("settings/todo_preferences.preferences_pb")
    return PreferenceDataStoreFactory.createWithPath {
        dataStoreFile.absolutePath.toPath()
    }
}