package org.example.todotask.data.models

import androidx.compose.ui.graphics.Color
import org.example.todotask.ui.theme.HighPriorityColor
import org.example.todotask.ui.theme.LowPriorityColor
import org.example.todotask.ui.theme.MediumPriorityColor
import org.example.todotask.ui.theme.NonePriorityColor

enum class Priority(val color:Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}