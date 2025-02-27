package org.example.todotask

import androidx.compose.ui.window.ComposeUIViewController
import org.example.todotask.di.initKoin

fun MainViewController() = ComposeUIViewController (
    configure = {
        initKoin()
    }
) {
    App()
}