package org.example.todotask.navigation.destination

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

import org.example.todotask.util.Actions
import org.example.todotask.navigation.Screen
import org.example.todotask.ui.screen.task.TaskScreen
import org.example.todotask.viewmodel.SharedViewModel

fun NavGraphBuilder.taskComposable(
    navigateToListScreen:(Actions) -> Unit,
    sharedViewModel : SharedViewModel
){
    composable<Screen.Task>(
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = {fullWidth -> -fullWidth},
                animationSpec = tween(
                    durationMillis = 400
                )
            )
        }
    ){ navBackStackTrace ->

        val taskId = navBackStackTrace.toRoute<Screen.Task>().id
        LaunchedEffect(key1 = taskId) {
            sharedViewModel.getSelectedTask(taskId)
        }
        val currentTask by sharedViewModel.selectedTask.collectAsState()
        LaunchedEffect(key1 = currentTask) {
            if (currentTask != null || taskId == -1)
                sharedViewModel.updateTask(currentTask)
        }

        TaskScreen(todoTask = currentTask, sharedViewModel = sharedViewModel,
            navigateListTaskScreen = navigateToListScreen)
    }
}