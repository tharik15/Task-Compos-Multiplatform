package org.example.todotask

import android.content.Context

object SharedModule {
    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

    fun getContext(): Context {
        return applicationContext
    }
}