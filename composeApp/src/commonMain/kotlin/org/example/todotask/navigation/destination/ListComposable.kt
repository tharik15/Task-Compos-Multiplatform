package org.example.todotask.navigation.destination

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

import org.example.todotask.util.Actions
import org.example.todotask.navigation.Screen
import org.example.todotask.ui.screen.list.ListScreen
import org.example.todotask.viewmodel.SharedViewModel

@ExperimentalAnimationApi
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen:(Int) -> Unit,
    sharedViewModel : SharedViewModel
){
    composable<Screen.List>{ navBackStackEntry ->
        val action = navBackStackEntry.toRoute<Screen.List>().actions

        var rememberAction by rememberSaveable { mutableStateOf(Actions.NO_ACTION) }

        LaunchedEffect(key1 = action) {
            if (action == Actions.NO_ACTION){
                sharedViewModel.resetSelectedTask()
            }
            if (action != rememberAction){
                rememberAction = action
                sharedViewModel.updateAction(action)
            }
        }
        //It will update only when add,update,delete the task,
        // That time only sharedViewModel.action value updated, Don't update Unnecessary
        val dataBaseAction = sharedViewModel.action
        ListScreen(
            actions = dataBaseAction,
            navigateToTaskScreen = navigateToTaskScreen,sharedViewModel = sharedViewModel)
    }
}