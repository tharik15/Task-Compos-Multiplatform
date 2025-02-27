package org.example.todotask.viewmodel

import androidx.lifecycle.ViewModel
import org.example.todotask.Platform
import org.example.todotask.data.UserRepository

class UserViewModel(
    private val repository: UserRepository,
    val platform: Platform
) :ViewModel(){

    fun sayHello(name: String): String {
        val foundUser = repository.findUser(name)
        return foundUser?.let { "Hello '$it' from ${platform.name}" } ?: "User '$name' not found!"
    }
}