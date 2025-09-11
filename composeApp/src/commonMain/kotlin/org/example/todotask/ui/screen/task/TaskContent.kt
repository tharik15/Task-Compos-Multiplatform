package org.example.todotask.ui.screen.task

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.ktor.utils.io.ByteReadChannel
import org.example.todotask.CameraController
import org.example.todotask.CaptureImageButton
import org.example.todotask.ui.theme.LARGE_PADDING
import org.example.todotask.ui.theme.MEDIUM_PADDING
import org.example.todotask.components.PriorityDropDown
import org.example.todotask.data.models.Priority
import org.example.todotask.loadImageBytesFromPath
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
fun TaskContent(modifier : Modifier = Modifier,
                title:String,
                onTitleChanges:(String) -> Unit,
                priority: Priority,
                onPrioritySelected:(Priority) -> Unit,
                description:String,
                onDescriptionChanged:(String) -> Unit,
                controller: CameraController
){
    var imagePath by remember { mutableStateOf<String?>(null) }

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
        HorizontalDivider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colorScheme.background
        )
        PriorityDropDown(priority = priority,
            onPrioritySelected = onPrioritySelected)

        OutlinedTextField(modifier = Modifier.fillMaxWidth()
            .height(150.dp),
            value = description,
            onValueChange = { onDescriptionChanged(it) },
            textStyle = MaterialTheme.typography.bodyMedium ,
            label = { Text(text = "Description") }
        )
        HorizontalDivider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colorScheme.background
        )

        Column(Modifier.padding(16.dp)) {
            CaptureImageButton(controller = controller , {
                imagePath = it
            },modifier)
            Spacer(Modifier.height(10.dp))
            imagePath?.let { path ->
                val imageBytes = remember(path) { loadImageBytesFromPath(path) }
                val channel = ByteReadChannel(imageBytes)
//                val painter = asyncPainterResource(data = channel)
//                KamelImage(
//                    resource = painter,
//                    contentDescription = "Profile"
//                )
            }
        }
    }
}

@Preview
@Composable
fun TaskContentPreview(){
    val controller = koinInject<CameraController>()
    TaskContent(modifier = Modifier.fillMaxSize(),"Title",
        {},
        Priority.LOW,
        {},
        "Description",
        {}, controller = controller)
}