package org.example.todotask

import android.app.Application
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.example.todotask.data.TodoDatabase
import org.example.todotask.data.dbFileName
import org.koin.core.scope.Scope

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

actual class Factory {
    actual fun createRoomDatabase(): TodoDatabase {
        val appContext = SharedModule.getContext()
        val dbFile = appContext.getDatabasePath(dbFileName)
        return Room.databaseBuilder<TodoDatabase>(
            context = appContext,
            name = dbFile.absolutePath,
        )
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}