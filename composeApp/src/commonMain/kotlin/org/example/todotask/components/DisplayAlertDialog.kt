package org.example.todotask.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DisplayAlertDialog(
    title:String,
    text:String,
    isDialogOpen:Boolean,
    onDismissClicked:()-> Unit,
    onConfirmClicked:() -> Unit
){
    if (isDialogOpen) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
            },
            text = {
                Text(
                    text =text,
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            },
            onDismissRequest = {
                onDismissClicked()
            },
            confirmButton = {
                Button(onClick = {
                    onConfirmClicked()
                    onDismissClicked()
                }) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { onDismissClicked() }) {
                    Text(text = "Cancel")
                }
            }
        )
    }

}