//package org.example.todotask.data
//
//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import androidx.room.Update
//
//import kotlinx.coroutines.flow.Flow
//import org.example.todotask.data.models.TodoTask
//
//@Dao
//interface TodoDao {
//
//    @Query("Select * from todo_table order by id ASC")
//    fun getAllTask():Flow<List<TodoTask>>
//
//    @Query("Select * from todo_table where id=:taskId")
//    fun getSelectedTask(taskId:Int):Flow<TodoTask>
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun addTask(task: TodoTask)
//
//    @Update
//    suspend fun updateTask(task: TodoTask)
//
//    @Delete
//    suspend fun deleteTask(task: TodoTask)
//
//    @Query("Delete from todo_table")
//    suspend fun deleteAllTask()
//
//    @Query("Select * from todo_table where title Like:searchText or description Like:searchText")
//    fun searchQuery(searchText:String):Flow<List<TodoTask>>
//
//    @Query("""
//        Select * from todo_table order by
//    case
//        when priority like 'L%' Then 1
//        when priority like 'M%' then 2
//        when priority like 'H%' then 3
//    end
//        """)
//    fun sortByLowPriority():Flow<List<TodoTask>>
//
//    @Query("""
//        Select * from todo_table order by
//    case
//        when priority like 'H%' Then 1
//        when priority like 'M%' then 2
//        when priority like 'L%' then 3
//    end
//    """)
//    fun sortByHighPriority():Flow<List<TodoTask>>
//
//}