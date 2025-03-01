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
import org.example.todotask.ui.screen.splash.SplashScreen
import org.example.todotask.viewmodel.SharedViewModel

@ExperimentalAnimationApi
fun NavGraphBuilder.splashComposable(
    navigateListScreen:(String) -> Unit
){
    composable<Screen.Splash>{ navBackStackEntry ->
//        val action = navBackStackEntry.toRoute<Screen.Splash>()

        SplashScreen(navigateListScreen)
    }
}