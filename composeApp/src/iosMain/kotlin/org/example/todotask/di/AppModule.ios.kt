package org.example.todotask.di

import org.example.todotask.createDataStore

import org.koin.dsl.module

actual fun dataStorePlatformModule() = module {
    single { createDataStore(this)  }
}