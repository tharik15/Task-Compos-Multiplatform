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

//package org.example.todotask
//
//import androidx.room.Room
//import androidx.sqlite.driver.bundled.BundledSQLiteDriver
//import app.cash.sqldelight.db.SqlDriver
//import app.cash.sqldelight.driver.native.NativeSqliteDriver
//import kotlinx.cinterop.ExperimentalForeignApi
//import kotlinx.cinterop.memScoped
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.IO
//import org.example.todotask.data.TodoDatabase
//import org.example.todotask.data.dbFileName
//import org.koin.core.scope.Scope
//import platform.Foundation.NSDocumentDirectory
//import platform.Foundation.NSFileManager
//import platform.Foundation.NSURL
//import platform.Foundation.NSUserDomainMask
//import platform.UIKit.UIAlertController
//import platform.UIKit.UIAlertControllerStyleAlert
//import platform.UIKit.UIApplication
//import platform.UIKit.UIDevice
//import platform.darwin.DISPATCH_TIME_NOW
//import platform.darwin.NSEC_PER_SEC
//import platform.darwin.dispatch_after
//import platform.darwin.dispatch_get_main_queue
//import platform.darwin.dispatch_time
//
//class IOSPlatform: Platform {
//    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
//}
//
//actual fun getPlatform(scope: Scope): Platform = IOSPlatform()
//
//
//@OptIn(ExperimentalForeignApi::class)
//actual fun showToast(message: String, context: Any) {
//    val alert = UIAlertController.alertControllerWithTitle(null, message, UIAlertControllerStyleAlert)
//    val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
//    rootViewController?.presentViewController(alert, animated = true, completion = null)
//    dispatch_after(dispatch_time(DISPATCH_TIME_NOW,  2 * NSEC_PER_SEC), dispatch_get_main_queue()) {
//        alert.dismissViewControllerAnimated(true, completion = null)
//    }
//}
//
//actual class Factory {
//    actual fun createRoomDatabase(): TodoDatabase {
//        val dbFile = "${fileDirectory()}/$dbFileName"
//        return Room.databaseBuilder<TodoDatabase>(
//            name = dbFile,
//        )
//            .setDriver(BundledSQLiteDriver())
//            .setQueryCoroutineContext(Dispatchers.IO)
//            .build()
//    }
//}
//
//@OptIn(ExperimentalForeignApi::class)
//private fun fileDirectory(): String {
//    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
//        directory = NSDocumentDirectory,
//        inDomain = NSUserDomainMask,
//        appropriateForURL = null,
//        create = false,
//        error = null,
//    )
//    return requireNotNull(documentDirectory).path!!
//}
actual fun showToast(message: String, context: Any) {
}

actual fun getPlatform(scope: Scope): Platform {
    TODO("Not yet implemented")
}
//todo_preferences
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