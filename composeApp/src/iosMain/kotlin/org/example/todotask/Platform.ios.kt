package org.example.todotask

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import io.ktor.http.ContentDisposition.Companion.File
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import org.koin.core.scope.Scope
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.*

actual fun showToast(message: String, context: Any) {
}

actual fun getPlatform(scope: Scope): Platform {
    TODO("Not yet implemented")
}

@OptIn(ExperimentalForeignApi::class)
actual fun createDataStore(scope: Scope): DataStore<Preferences> {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )
    require(documentDirectory != null) { "Document directory not found" }
    val filePath = documentDirectory!!.URLByAppendingPathComponent("todo_preferences.preferences_pb")?.path
    return PreferenceDataStoreFactory.createWithPath {
        filePath!!.toPath()
    }
}


