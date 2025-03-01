package org.example.todotask

import android.app.Application
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import kotlinx.coroutines.Dispatchers
import okio.Path.Companion.toPath
import org.example.todotask.data.dbFileName
import org.koin.core.scope.Scope
import org.koin.core.scope.get

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

//actual class Factory {
//    actual fun createRoomDatabase(): TodoDatabase {
//        val appContext = SharedModule.getContext()
//        val dbFile = appContext.getDatabasePath(dbFileName)
//        return Room.databaseBuilder<TodoDatabase>(
//            context = appContext,
//            name = dbFile.absolutePath,
//        )
//            .setDriver(BundledSQLiteDriver())
//            .setQueryCoroutineContext(Dispatchers.IO)
//            .build()
//    }
//}
actual fun createDataStore(scope: Scope): DataStore<Preferences> {
    val context:Context = scope.get()
    return PreferenceDataStoreFactory.createWithPath {
        (context as MainApplication).applicationContext.filesDir.resolve("todo_preferences.preferences_pb").absolutePath.toPath()
    }
}