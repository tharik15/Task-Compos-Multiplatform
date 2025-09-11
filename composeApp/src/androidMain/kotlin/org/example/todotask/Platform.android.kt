package org.example.todotask

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import okio.Path.Companion.toPath
import org.koin.core.scope.Scope
import java.io.File

class AndroidPlatform(context: Context) : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT} - context:$context"
}

actual fun getPlatform(scope: Scope): Platform = AndroidPlatform(scope.get())

@Composable
fun ShowToast(message: String) {
    val context = LocalContext.current
    showToast(message, context)
}

actual fun showToast(message: String, context: Any) {
    Toast.makeText(context as Context, message, Toast.LENGTH_SHORT).show()
}

actual fun createDataStore(scope: Scope): DataStore<Preferences> {
    val context:Context = scope.get()
    return PreferenceDataStoreFactory.createWithPath {
        (context as MainApplication).applicationContext.filesDir.resolve("todo_preferences.preferences_pb").absolutePath.toPath()
    }
}

actual fun loadImageBytesFromPath(path: String): ByteArray {
    val file = File(path)
    return if (file.exists()) file.readBytes() else ByteArray(0)
}