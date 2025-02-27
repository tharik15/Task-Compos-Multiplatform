package org.example.todotask

import android.app.Application
import org.example.todotask.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent

class MainApplication : Application(),KoinComponent {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MainApplication)
            androidLogger()
        }
        // Initialize your shared module with the application context
        SharedModule.init(this)
    }
}