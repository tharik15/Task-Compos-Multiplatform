package org.example.todotask

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.todotask.di.initKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Task compose app",
    ) {
        initKoin()
        App()
    }
}