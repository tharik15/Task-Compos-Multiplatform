//package org.example.todotask.db
//
//import android.content.Context
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.sqlite.driver.bundled.BundledSQLiteDriver
//import kotlinx.coroutines.Dispatchers
//import org.example.todotask.data.TodoDatabase
//import org.example.todotask.data.dbFileName
//import org.example.todotask.util.Constants.DATABASE_NAME
//
//fun getDatabaseBuilder(ctx: Context): TodoDatabase {
//    val dbFile = ctx.getDatabasePath(dbFileName)
//    return Room.databaseBuilder<TodoDatabase>(ctx, dbFile.absolutePath)
//        .setDriver(BundledSQLiteDriver())
//        .setQueryCoroutineContext(Dispatchers.IO)
//        .build()
//}