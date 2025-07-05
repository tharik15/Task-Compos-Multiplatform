package org.example.todotask.ui.screen.task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.example.todotask.data.models.TodoTask
import org.example.todotask.util.Actions
import org.example.todotask.components.DisplayAlertDialog
import org.example.todotask.data.models.Priority
import org.example.todotask.resources.Res
import org.example.todotask.resources.add_task
import org.example.todotask.resources.alert_dialog_text
import org.example.todotask.resources.alert_dialog_title
import org.example.todotask.resources.back_icon
import org.example.todotask.resources.close_icon
import org.example.todotask.resources.delete_icon
import org.example.todotask.resources.update_icon
import org.example.todotask.viewmodel.SharedViewModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TaskAppBar(
    todoTask: TodoTask?,
    navigateListTaskScreen: (String) -> Unit
){

    if (todoTask == null){
        AddTaskAppBar(navigateListTaskScreen = navigateListTaskScreen)
    }else{
        ExistingTaskAppBar(todoTask,navigateListTaskScreen = navigateListTaskScreen)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskAppBar(navigateListTaskScreen:(String) -> Unit){
    TopAppBar(
        title = { Text(text = stringResource(Res.string.add_task)) },
        navigationIcon = {
            BackArrow(onBackClicked = navigateListTaskScreen)
        },
        actions = {
            DoneNewTask(onAddClicked = navigateListTaskScreen)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.primaryContainer)
    )
}

@Composable
fun BackArrow(onBackClicked:(String) -> Unit){
    IconButton(onClick = { onBackClicked(Actions.NO_ACTION)  }) {
        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(Res.string.back_icon),
            tint = MaterialTheme.colorScheme.surfaceTint
        )
    }
}

@Composable
fun CloseIcon(onCloseClicked:(String) -> Unit){
    IconButton(onClick = { onCloseClicked(Actions.NO_ACTION)  }) {
        Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(Res.string.close_icon),
            tint = MaterialTheme.colorScheme.surfaceTint
        )
    }
}


@Composable
fun DoneNewTask(onAddClicked:(String) -> Unit){
    IconButton(onClick = { onAddClicked(Actions.ADD)  }) {
        Icon(imageVector = Icons.Filled.Check, contentDescription = stringResource(Res.string.add_task),
            tint = MaterialTheme.colorScheme.surfaceTint
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExistingTaskAppBar(todoTask: TodoTask,
                       navigateListTaskScreen:(String) -> Unit){
    TopAppBar(
        title = { Text(text = todoTask.title) },
        navigationIcon = {
            CloseIcon(onCloseClicked = navigateListTaskScreen)
        },
        actions = {
            ExistingAppBarActions(todoTask = todoTask,
                navigateListTaskScreen = navigateListTaskScreen)

        },
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.primaryContainer)
    )
}

@Composable
fun ExistingAppBarActions(
    todoTask: TodoTask,
    navigateListTaskScreen:(String) -> Unit
){
    var isDialogOpen by remember { mutableStateOf(false) }

    DisplayAlertDialog(
        title = stringResource(Res.string.alert_dialog_title,todoTask.title),
        text = stringResource(Res.string.alert_dialog_text,todoTask.title),
        isDialogOpen = isDialogOpen,
        onDismissClicked = {
            isDialogOpen = false
        },
        onConfirmClicked = {
            navigateListTaskScreen(Actions.DELETE)
        })
    DeleteTask(onDeleteClicked = {
        isDialogOpen = true
    })

    UpdateTask(onUpdateClicked = navigateListTaskScreen)
}

@Composable
fun DeleteTask(onDeleteClicked:() -> Unit){
    IconButton(onClick = { onDeleteClicked() }) {
        Icon(imageVector = Icons.Filled.Delete, contentDescription = stringResource(Res.string.delete_icon),
            tint = MaterialTheme.colorScheme.surfaceTint
        )
    }
}

@Composable
fun UpdateTask(onUpdateClicked:(String) -> Unit){
    IconButton(onClick = { onUpdateClicked(Actions.UPDATE)  }) {
        Icon(imageVector = Icons.Filled.Done, contentDescription = stringResource(Res.string.update_icon),
            tint = MaterialTheme.colorScheme.surfaceTint
        )
    }
}


@Composable
@Preview
fun AddTaskAppBarPreview(){
    AddTaskAppBar(navigateListTaskScreen = {

    })
}

@Composable
@Preview
fun UpdateTaskAppBarPreview(){
    ExistingTaskAppBar(todoTask = TodoTask(4,"Test","test call", Priority.LOW), navigateListTaskScreen = {

    })
}