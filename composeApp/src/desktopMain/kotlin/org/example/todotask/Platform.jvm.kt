package org.example.todotask

import org.koin.core.scope.Scope

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(scope: Scope): Platform = JVMPlatform()