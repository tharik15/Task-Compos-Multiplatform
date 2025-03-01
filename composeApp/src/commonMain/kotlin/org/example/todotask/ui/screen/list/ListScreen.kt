package org.example.todotask.ui.screen.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import org.example.todotask.util.Actions
import org.example.todotask.util.SearchAppBarState
import kotlinx.coroutines.launch
import org.example.todotask.resources.Res
import org.example.todotask.resources.add_task
import org.example.todotask.viewmodel.SharedViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun ListScreen(
    actions: String,
    navigateToTaskScreen :(Int) -> Unit,
    sharedViewModel : SharedViewModel
){

    LaunchedEffect(key1 = actions) {
        sharedViewModel.callDatabaseAction(actions)
    }

    val allTask by sharedViewModel.allTask.collectAsState()
    val searchTask by sharedViewModel.searchTask.collectAsState()
    val sortState by sharedViewModel.sortState.collectAsState()
    val searchAppBarState: SearchAppBarState = sharedViewModel.searchAppBarState
    val searchTextState:String = sharedViewModel.searchTextState

    val lowPriorityTask by sharedViewModel.lowPriorityTask.collectAsState()
    val highPriorityTask by sharedViewModel.highPriorityTask.collectAsState()

    val snackBarHostState =  remember { SnackbarHostState() }
    DisplaySnackBar(
        snackBarHostState = snackBarHostState,
        title = sharedViewModel.title,
        actions = actions,
        onComplete = { sharedViewModel.updateAction(it) },
        onUndoClicked = { actions ->
            sharedViewModel.updateAction(actions)
        }
    )

    Scaffold(
        snackbarHost = {
                   SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState,
                onSearchClicked = {
                    sharedViewModel.getSearchTask(it)
                }
            )
        },
        content = { padding ->
            ListContent(
                modifier = Modifier.padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
                taskList = allTask,
                searchTask = searchTask,
                lowPriorityTask = lowPriorityTask,
                highPriorityTask = highPriorityTask,
                sortState = sortState,
                navigateToTaskScreen = navigateToTaskScreen,
                searchAppBarState = searchAppBarState,
                swipeToDelete = { actions, todoTask ->
                    sharedViewModel.updateAction(actions)
                    sharedViewModel.updateTask(todoTask)
                    snackBarHostState.currentSnackbarData?.dismiss()
                }
            )
        },
        floatingActionButton = {
            FabButton(navigateToTaskScreen)
        }
    )

    LifecycleAwareComposable(
        onStart = {
            println("LifecycleAwareComposable\",\"Handle onStart event")
        },
        onStop = {
            println("\"LifecycleAwareComposable\",\"Handle onStop event\"")
        }
    )
}

@Composable
fun FabButton(
    onFabClicked :(Int) -> Unit
){
    FloatingActionButton(onClick = { onFabClicked(-1) }) {
        Icon(imageVector = Icons.Filled.Add,
            contentDescription = stringResource(Res.string.add_task),
            tint = Color.Red
        )
    }
}

@Composable
fun DisplaySnackBar(
    snackBarHostState:SnackbarHostState,
    title:String,
    actions: String,
    onComplete:(String) -> Unit,
    onUndoClicked:(String) -> Unit
){

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = actions) {
        scope.launch {
            if (actions != Actions.NO_ACTION){
                val snackBarResult = snackBarHostState.showSnackbar(
                    getMessage(actions,title),
                    actionLabel = getSelectedState(actions),
                    duration = SnackbarDuration.Short
                )
                if (actions == Actions.DELETE && snackBarResult == SnackbarResult.ActionPerformed){
                    onUndoClicked(Actions.UNDO)
                }else if (snackBarResult == SnackbarResult.Dismissed || actions != Actions.DELETE){
                    onComplete(Actions.NO_ACTION)
                }
            }
        }
    }
}

private fun getMessage(actions: String, title: String):String{
    return if (actions == "DELETE_ALL"){
        "All Tasks Deleted.."
    }else{
        "${actions} $title"
    }
}

private fun getSelectedState(actions: String):String{
    return if (actions == "DELETE"){
        "Undo"
    }else{
        "OK"
    }
}

@Composable
fun LifecycleAwareComposable(
    onStart: () -> Unit,
    onStop: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> onStart()
                Lifecycle.Event.ON_STOP -> onStop()
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

/*
@Composable
@Preview
fun ListPreview(){
    ListScreen(navigateToTaskScreen = {})
}*/
