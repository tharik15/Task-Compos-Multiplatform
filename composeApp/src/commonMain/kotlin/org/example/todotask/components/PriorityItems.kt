package org.example.todotask.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.example.todotask.data.models.Priority

import org.example.todotask.util.LARGE_PADDING
import org.example.todotask.util.PRIORITY_INDICATOR_SIZE
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PriorityItem(priority: Priority){

    Row (verticalAlignment = Alignment.CenterVertically){
        androidx.compose.foundation.Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)){
            drawCircle(color = priority.color)
        }
        Text(text = priority.name,
            Modifier.padding(LARGE_PADDING),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary)
    }

}

@Preview
@Composable
fun PriorityItemPreview(){

    PriorityItem(priority = Priority.NONE)
}