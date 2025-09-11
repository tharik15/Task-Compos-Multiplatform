package org.example.todotask

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.reinterpret
import okio.Path.Companion.toPath
import org.koin.core.scope.Scope
import platform.Foundation.NSData
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.dataWithContentsOfFile
import platform.darwin.ByteVar
import kotlin.Any
import kotlin.OptIn
import kotlin.String
import kotlin.TODO
import kotlin.require

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

@OptIn(ExperimentalForeignApi::class)
actual fun loadImageBytesFromPath(path: String): ByteArray {
    val nsData: NSData? = NSData.dataWithContentsOfFile(path)
    if (nsData == null || nsData.length.toInt() == 0) return ByteArray(0)

    val length = nsData.length.toInt()
    val byteArray = ByteArray(length)

    memScoped {
        val buffer = nsData.bytes?.reinterpret<ByteVar>() ?: return ByteArray(0)
        for (i in 0 until length) {
            byteArray[i] = buffer[i].toByte()
        }
    }

    return byteArray
}