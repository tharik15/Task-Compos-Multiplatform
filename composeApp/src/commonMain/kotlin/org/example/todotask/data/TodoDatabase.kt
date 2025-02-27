package org.example.todotask.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composetodo.data.models.TodoTask

import org.example.todotask.data.TodoDao

@Database(
     entities = [TodoTask::class], version = 1
)
abstract class TodoDatabase : RoomDatabase(){

    abstract fun todoDao(): TodoDao

}

internal const val dbFileName = "Todo.db"