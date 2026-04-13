package org.example.todotask.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.todotask.data.models.TodoTask
import kotlin.coroutines.CoroutineContext

interface ToDoRepository {
   suspend fun getSelectedTask(taskId: Int): Flow<TodoTask>
   suspend fun addTask(task: TodoTask)
   suspend fun update(task: TodoTask)
   suspend fun deleteTask(task: TodoTask)
   suspend fun deleteAllTask()
   suspend fun searchDataBase(searchQuery:String,context: CoroutineContext): Flow<List<TodoTask>>

}