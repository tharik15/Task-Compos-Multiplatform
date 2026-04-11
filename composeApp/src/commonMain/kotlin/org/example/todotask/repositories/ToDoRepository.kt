package org.example.todotask.repositories

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.todotask.data.DatabaseDriver
import org.example.todotask.data.models.TodoTask

import org.example.todotask.data.models.Priority
import org.example.todotask.db.TodoDatabase
import org.example.todotask.db.Todo_table

class ToDoRepository (
    private val databaseDriver: DatabaseDriver
){

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    val database = TodoDatabase(databaseDriver.createDriver())
    private val databaseQuery = database.todoDatabaseQueries

    val getAllTask : Flow<List<TodoTask>> =
        databaseQuery.selectAllTasks().asFlow().mapToList(dispatcher).map {
            it.map { task ->
                task.mapPriority()
            }
        }

    fun Todo_table.mapPriority(): TodoTask {
        return TodoTask(id = this.id.toInt(), title = this.title, description = this.description, priority = mapPriority(this.priority))
    }

    fun mapPriority(priority: String): Priority {
        return when (priority) {
            "LOW" -> Priority.LOW
            "MEDIUM" -> Priority.MEDIUM
            "HIGH" -> Priority.HIGH
            else -> throw IllegalArgumentException("Unknown priority: $priority") // Handle unexpected values
        }
    }


    val sortByLowPriority : Flow<List<TodoTask>> = mapFlow(databaseQuery.sortByLowPriority())
    val sortByHighPriority : Flow<List<TodoTask>> = mapFlow(databaseQuery.sortByHighPriority())

    fun getSelectedTask(taskId: Int):Flow<TodoTask>{
        return mapFlow(databaseQuery.selectTaskById(taskId.toLong()),true)
    }

    fun addTask(task: TodoTask){
        databaseQuery.insertTask(id = 1,title = task.title, description = task.description, priority = task.priority.name)
    }

    fun update(task: TodoTask){
        databaseQuery.updateTask(title = task.title, description = task.description, priority = task.priority.name,task.id.toLong())
    }

    fun deleteTask(task: TodoTask){
        databaseQuery.deleteTask(task.id.toLong())
    }

    fun deleteAllTask(){
        databaseQuery.deleteAllTasks()
    }

    fun searchDataBase(searchQuery:String):Flow<List<TodoTask>>{
        return mapFlow(databaseQuery.searchTasks(title = searchQuery, description = searchQuery))
    }

    fun mapFlow(query: Query<Todo_table>):Flow<List<TodoTask>>{
        return query.asFlow().mapToList(dispatcher).map {
            it.map {
                TodoTask(id = it.id.toInt(), title = it.title, description = it.description,mapPriority(it.priority))
            }
        }
    }

    fun mapFlow(query: Query<Todo_table>,isSingleValue:Boolean):Flow<TodoTask>{
        return query.asFlow().map  {
            TodoTask(id = it.executeAsOne().id.toInt(), title = it.executeAsOne().title, description = it.executeAsOne().description,mapPriority(it.executeAsOne().priority))
        }
    }
}