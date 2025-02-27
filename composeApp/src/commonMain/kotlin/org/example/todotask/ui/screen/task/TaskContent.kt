package org.example.todotask.ui.screen.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.todotask.ui.theme.LARGE_PADDING
import org.example.todotask.ui.theme.MEDIUM_PADDING
import org.example.todotask.components.PriorityDropDown
import org.example.todotask.data.models.Priority
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TaskContent(modifier : Modifier = Modifier,
                title:String,
                onTitleChanges:(String) -> Unit,
                priority: Priority,
                onPrioritySelected:(Priority) -> Unit,
                description:String,
                onDescriptionChanged:(String) -> Unit
){
    Column(modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(all = LARGE_PADDING)
    ) {

        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChanges(it) },
            textStyle = MaterialTheme.typography.bodyMedium ,
            label = { Text(text = "Title") },
            singleLine = true
        )
        Divider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colorScheme.background
        )
        PriorityDropDown(priority = priority,
            onPrioritySelected = onPrioritySelected)

        OutlinedTextField(modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { onDescriptionChanged(it) },
            textStyle = MaterialTheme.typography.bodyMedium ,
            label = { Text(text = "Description") }
        )

    }

}


@Composable
@Preview
fun TaskContentPreview(){
    TaskContent(modifier = Modifier.fillMaxSize(),"Title",
        {},
        Priority.LOW,
        {},
        "Description",
        {})
}