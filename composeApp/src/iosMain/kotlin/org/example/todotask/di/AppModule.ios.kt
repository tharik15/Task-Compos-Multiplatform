package org.example.todotask.di

import org.example.todotask.CameraController
import org.example.todotask.createDataStore

import org.koin.dsl.module

actual fun dataStorePlatformModule() = module {
    single { createDataStore(this)  }
}

actual fun controllerModule() = module {
    single { CameraController(this) }
}