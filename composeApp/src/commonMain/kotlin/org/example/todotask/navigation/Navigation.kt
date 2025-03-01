package org.example.todotask.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import org.example.todotask.navigation.destination.listComposable
import org.example.todotask.navigation.destination.splashComposable
import org.example.todotask.navigation.destination.taskComposable
import org.example.todotask.viewmodel.SharedViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavigation(navController: NavHostController,
                    sharedViewModel : SharedViewModel
){

    NavHost(navController = navController,
        startDestination = Screen.List()){

        splashComposable { action ->
            navController.navigate(Screen.List(actions = action))
        }

        listComposable(
            navigateToTaskScreen = {taskId ->
                navController.navigate(Screen.Task(id = taskId))
            },
            sharedViewModel = sharedViewModel
        )
        taskComposable (
            navigateToListScreen = {action ->
               navController.navigate(Screen.List(actions = action)){
                   popUpTo(Screen.List()){ inclusive = true }
               }

            },
            sharedViewModel = sharedViewModel
        )
    }
}