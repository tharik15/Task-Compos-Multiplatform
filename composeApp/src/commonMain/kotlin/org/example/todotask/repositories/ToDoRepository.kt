package org.example.todotask.repositories

import com.example.composetodo.data.models.TodoTask
import org.example.todotask.data.TodoDao
import kotlinx.coroutines.flow.Flow
import org.example.todotask.data.TodoDatabase

class ToDoRepository (private val todoDatabase: TodoDatabase) {
    val  todoDao = todoDatabase.todoDao()
    val getAllTask : Flow<List<TodoTask>> = todoDao.getAllTask()
    val sortByLowPriority : Flow<List<TodoTask>> = todoDao.sortByLowPriority()
    val sortByHighPriority : Flow<List<TodoTask>> = todoDao.sortByHighPriority()

    fun getSelectedTask(taskId: Int):Flow<TodoTask>{
        return todoDao.getSelectedTask(taskId)
    }

    suspend fun addTask(task: TodoTask){
        todoDao.addTask(task)
    }

    suspend fun update(task: TodoTask){
        todoDao.updateTask(task)
    }

    suspend fun deleteTask(task: TodoTask){
        todoDao.deleteTask(task)
    }

    suspend fun deleteAllTask(){
        todoDao.deleteAllTask()
    }

    suspend fun searchDataBase(searchQuery:String):Flow<List<TodoTask>>{
        return todoDao.searchQuery(searchQuery)
    }
}