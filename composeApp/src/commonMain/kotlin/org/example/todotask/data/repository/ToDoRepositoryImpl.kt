package org.example.todotask.data.repository

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.todotask.data.DatabaseDriver
import org.example.todotask.data.models.Priority
import org.example.todotask.data.models.TodoTask
import org.example.todotask.db.TodoDatabase
import org.example.todotask.db.Todo_table
import org.example.todotask.domain.repository.ToDoRepository
import kotlin.coroutines.CoroutineContext

class ToDoRepositoryImpl (
    databaseDriver: DatabaseDriver):ToDoRepository{

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    val database = TodoDatabase.Companion(databaseDriver.createDriver())
    private val databaseQuery = database.todoDatabaseQueries

    val getAllTask : Flow<List<TodoTask>> =
        databaseQuery.selectAllTasks().asFlow().mapToList(dispatcher).map {
            it.map { task ->
                task.mapPriority()
            }
        }

    fun Todo_table.mapPriority(): TodoTask {
        return TodoTask(
            id = this.id.toInt(),
            title = this.title,
            description = this.description,
            priority = mapPriority(this.priority)
        )
    }

    fun mapPriority(priority: String): Priority {
        return when (priority) {
            "LOW" -> Priority.LOW
            "MEDIUM" -> Priority.MEDIUM
            "HIGH" -> Priority.HIGH
            else -> throw IllegalArgumentException("Unknown priority: $priority") // Handle unexpected values
        }
    }


    fun sortByLowPriority(context: CoroutineContext) : Flow<List<TodoTask>> = mapFlowSingle(context,databaseQuery.sortByLowPriority())
    fun sortByHighPriority(context: CoroutineContext) : Flow<List<TodoTask>> = mapFlowSingle(context,databaseQuery.sortByHighPriority())

   override suspend fun getSelectedTask(taskId: Int): Flow<TodoTask> {
        return mapFlowSingle(databaseQuery.selectTaskById(taskId.toLong()))
    }

    override suspend fun addTask(task: TodoTask){
        databaseQuery.insertTask(id = 1,title = task.title, description = task.description, priority = task.priority.name)
    }

    override suspend fun update(task: TodoTask){
        databaseQuery.updateTask(title = task.title, description = task.description, priority = task.priority.name,task.id.toLong())
    }

    override suspend fun deleteTask(task: TodoTask){
        databaseQuery.deleteTask(task.id.toLong())
    }

    override suspend fun deleteAllTask(){
        databaseQuery.deleteAllTasks()
    }

    override suspend fun searchDataBase(searchQuery:String,context: CoroutineContext): Flow<List<TodoTask>> {
        return mapFlowSingle(context,databaseQuery.searchTasks(title = searchQuery, description = searchQuery))
    }

    fun mapFlowSingle(context:CoroutineContext, query: Query<Todo_table>): Flow<List<TodoTask>> {
        return query.asFlow().mapToList(context).map {
            it.map { task ->
                TodoTask(
                    id = task.id.toInt(),
                    title = task.title,
                    description = task.description,
                    mapPriority(task.priority)
                )
            }
        }
    }

    fun mapFlowSingle(query: Query<Todo_table>): Flow<TodoTask> {
        return query.asFlow().map  {
            TodoTask(
                id = it.executeAsOne().id.toInt(),
                title = it.executeAsOne().title,
                description = it.executeAsOne().description,
                mapPriority(it.executeAsOne().priority)
            )
        }
    }
}