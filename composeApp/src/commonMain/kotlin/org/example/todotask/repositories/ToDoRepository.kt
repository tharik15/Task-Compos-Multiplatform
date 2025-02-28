package org.example.todotask.repositories

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.todotask.data.models.TodoTask

import org.example.todotask.data.models.Priority
import orgexampletodotaskdb.Todo_table

class ToDoRepository (private val database: org.example.todotask.db.TodoDatabase) {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    private val databaseQuery = database.todoDataBaseQueries

    val getAllTask : Flow<List<TodoTask>> =
        database.todoDataBaseQueries.selectAllTasks().asFlow().mapToList(dispatcher).map {
            it.map {
                TodoTask(id = it.id.toInt(), title = it.title, description = it.description,mapPriority(it.priority))
            }
        }

    fun mapPriority(priority: String): Priority {
        return when (priority) {
            "LOW" -> Priority.LOW
            "MEDIUM" -> Priority.MEDIUM
            "HIGH" -> Priority.HIGH
            else -> throw IllegalArgumentException("Unknown priority: $priority") // Handle unexpected values
        }
    }


    val sortByLowPriority : Flow<List<TodoTask>> = mapFlow(database.todoDataBaseQueries.sortByLowPriority())
    val sortByHighPriority : Flow<List<TodoTask>> = mapFlow(database.todoDataBaseQueries.sortByHighPriority())

    fun getSelectedTask(taskId: Int):Flow<TodoTask>{
        return mapFlow(database.todoDataBaseQueries.selectTaskById(taskId.toLong()),true)
    }

    suspend fun addTask(task: TodoTask){
        database.todoDataBaseQueries.insertTask(task.title,task.description,task.priority.name)
    }

    suspend fun update(task: TodoTask){
        databaseQuery.updateTask(title = task.title, description = task.description, priority = task.priority.name,task.id.toLong())
    }

    suspend fun deleteTask(task: TodoTask){
        databaseQuery.deleteTask(task.id.toLong())
    }

    suspend fun deleteAllTask(){
        databaseQuery.deleteAllTasks()
    }

    suspend fun searchDataBase(searchQuery:String):Flow<List<TodoTask>>{
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