package org.example.todotask.navigation

import org.example.todotask.util.Actions
import kotlinx.serialization.Serializable


@Serializable
sealed class Screen{
    @Serializable
    data class List(val actions: String = Actions.NO_ACTION):Screen()
    @Serializable
    data class Task(val id: Int):Screen()
    @Serializable
    data class Image(val id: Int):Screen()
    @Serializable
    data class NavigationBar(val id: Int):Screen()

}