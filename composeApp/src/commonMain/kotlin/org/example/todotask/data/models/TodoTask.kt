package org.example.todotask.data.models

import org.example.todotask.data.models.Priority
import org.example.todotask.util.Constants.DATABASE_TABLE


data class TodoTask (
    val id:Int = 0,
    val title:String,
    val description:String,
    val priority: Priority
)