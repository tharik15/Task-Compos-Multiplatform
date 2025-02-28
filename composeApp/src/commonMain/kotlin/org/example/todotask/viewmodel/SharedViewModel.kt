package org.example.todotask.viewmodel

//import org.example.todotask.repositories.DataStoreRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.todotask.data.models.TodoTask
import org.example.todotask.data.models.Priority
import org.example.todotask.repositories.ToDoRepository
import org.example.todotask.ui.theme.TITLE_MAX_LENGHT
import org.example.todotask.util.Actions
import org.example.todotask.util.RequestState
import org.example.todotask.util.SearchAppBarState
import org.koin.core.component.KoinComponent

class SharedViewModel
constructor(private val toDoRepository: ToDoRepository
//, private val dataStoreRepository: DataStoreRepository
) : ViewModel(),KoinComponent {


    var id  by mutableIntStateOf(-1)
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var priority by mutableStateOf(Priority.LOW)
        private set
    var searchAppBarState by mutableStateOf(SearchAppBarState.CLOSED)
        private set
    var searchTextState by mutableStateOf("")
        private set

    var action by mutableStateOf(Actions.NO_ACTION)
        private set

    private val _allTask = MutableStateFlow<RequestState<List<TodoTask>>>(RequestState.Idle)
    val allTask : StateFlow<RequestState<List<TodoTask>>> = _allTask

    private val _searchTask = MutableStateFlow<RequestState<List<TodoTask>>>(RequestState.Idle)
    val searchTask : StateFlow<RequestState<List<TodoTask>>> = _searchTask

    private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState : StateFlow<RequestState<Priority>> = _sortState

    val lowPriorityTask : StateFlow<List<TodoTask>> =
        toDoRepository.sortByLowPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            arrayListOf()
        )

    val highPriorityTask : StateFlow<List<TodoTask>> =
        toDoRepository.sortByHighPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            arrayListOf()
        )


    init {
        getAllTask()
        readSortTask()
    }

    private fun readSortTask(){
        _sortState.value = RequestState.Idle
        _sortState.value = RequestState.Success(Priority.NONE)
        /*try {
            viewModelScope.launch {
                dataStoreRepository.readStoreState.
               map {
                   Priority.valueOf(it)
               }.collect{
                   _sortState.value = RequestState.Success(it)
                }
            }
        }catch (e:Exception){
            _sortState.value = RequestState.Failure(e)
        }*/

    }
    fun persistSortState(priority: Priority){
        viewModelScope.launch {
//            dataStoreRepository.persistSortState(priority)
        }
    }

    private fun getAllTask(){
        _allTask.value = RequestState.Idle
        try {
            viewModelScope.launch {
                toDoRepository.getAllTask.collect{
                    _allTask.value = RequestState.Success(it)
                }
            }
        }catch (e:Exception){
            _allTask.value = RequestState.Failure(e)
        }

    }

    val _selectedTask:MutableStateFlow<TodoTask?> = MutableStateFlow<TodoTask?>(null)
    val selectedTask : StateFlow<TodoTask?> = _selectedTask

    fun getSearchTask(searchQuery:String){
        _searchTask.value = RequestState.Idle
        try {
            viewModelScope.launch {
                toDoRepository.searchDataBase(searchQuery = "%${searchQuery}%").collect{
                    _searchTask.value = RequestState.Success(it)
                }
            }
        }catch (e:Exception){
            _searchTask.value = RequestState.Failure(e)
        }
        searchAppBarState = SearchAppBarState.TRIGGERED
    }

    fun getSelectedTask(taskId:Int){
        try {
            viewModelScope.launch {
                try {
                    toDoRepository.getSelectedTask(taskId).collect{ task ->
                        _selectedTask.value = task
                    }
                }catch (e:Exception){
                    println(e)
                }

            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun updateTask(todoTask: TodoTask?){
        if (todoTask != null){
            id = todoTask.id
            title = todoTask.title
            description = todoTask.description
            priority = todoTask.priority
        }else{
            id = -1
            title = ""
            description = ""
            priority = Priority.LOW
        }
    }

    fun updateTitleByLength(title:String){
        if (title.length < TITLE_MAX_LENGHT){
            this.title = title
        }
    }

    fun isValidation():Boolean{
        return title.isNotEmpty() && description.isNotEmpty()
    }

    private fun addTask(){
        viewModelScope.launch(Dispatchers.Default) {
            val todoTask = TodoTask(
                title = title,
                description = description,
                priority = priority)
            toDoRepository.addTask(todoTask)
        }
        searchAppBarState = SearchAppBarState.CLOSED
    }

    private fun updateTask(){
        viewModelScope.launch(Dispatchers.Default) {
            val todoTask = TodoTask(
                id = id,
                title = title,
                description = description,
                priority = priority)
            toDoRepository.update(todoTask)
        }
    }

    private fun deleteTask(){
        viewModelScope.launch(Dispatchers.Default) {
            val todoTask = TodoTask(
                id = id,
                title = title,
                description = description,
                priority = priority)
            toDoRepository.deleteTask(todoTask)
        }
    }

    private fun deleteAllTask(){
        viewModelScope.launch(Dispatchers.Default) {
            toDoRepository.deleteAllTask()
        }
    }

    fun callDatabaseAction(actions: String){
        when(actions){
            Actions.ADD -> {
                addTask()
            }
            Actions.UPDATE -> {
                updateTask()
            }
            Actions.DELETE -> {
                deleteTask()
            }
            Actions.DELETE_ALL -> {
                deleteAllTask()
            }
            Actions.UNDO -> {
                addTask()
            }
            else ->{}
        }
    }

    fun updatePriority(newPriority:Priority){
        priority = newPriority
    }
    fun updateDescription(newDesc:String){
        description = newDesc
    }
    fun updateTitle(newTitle:String){
        title = newTitle
    }
    fun updateAction(actions: String){
        action = actions
    }

    fun updateAppBarState(newState: SearchAppBarState) {
        searchAppBarState = newState
    }

    fun updateSearchText(newText: String) {
        searchTextState = newText
    }
}