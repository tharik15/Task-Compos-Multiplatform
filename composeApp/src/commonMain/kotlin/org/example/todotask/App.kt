package org.example.todotask

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.example.todotask.navigation.SetupNavigation
import org.example.todotask.viewmodel.SharedViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    val sharedViewModel = koinViewModel<SharedViewModel>()
    MaterialTheme {
        SetupNavigation(
            navController = navController,
            sharedViewModel = sharedViewModel)
    }
}