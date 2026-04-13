package org.example.todotask

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.darkScheme
import com.example.compose.lightScheme
import org.example.todotask.navigation.SetupNavigation
import org.example.todotask.ui.theme.Typography
import org.example.todotask.ui.screen.viewmodel.SharedViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    val sharedViewModel = koinViewModel<SharedViewModel>()
    val colorScheme = if (isSystemInDarkTheme()) darkScheme else lightScheme
    val typography = Typography
    MaterialTheme(colorScheme = colorScheme,
        typography = typography) {
        SetupNavigation(
            navController = navController,
            sharedViewModel = sharedViewModel)
    }
}