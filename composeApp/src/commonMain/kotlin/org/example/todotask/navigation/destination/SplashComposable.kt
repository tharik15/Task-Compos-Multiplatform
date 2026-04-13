package org.example.todotask.navigation.destination

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.example.todotask.navigation.Screen
import org.example.todotask.ui.screen.splash.SplashScreen

@ExperimentalAnimationApi
fun NavGraphBuilder.splashComposable(
    navigateListScreen:(String) -> Unit
){
    composable<Screen.Splash>{ navBackStackEntry ->
//        val action = navBackStackEntry.toRoute<Screen.Splash>()

        SplashScreen(navigateListScreen)
    }
}