package org.example.todotask

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

@Composable
actual fun CaptureImageButton(
    controller: CameraController,
    onCaptured: (String?) -> Unit,
    modifier: Modifier
) {
    Button(onClick = {
        val dialog = FileDialog(Frame(), "Select Image", FileDialog.LOAD)
        dialog.isVisible = true
        val path = dialog.file?.let { File(dialog.directory, it).absolutePath }
        onCaptured(path)
    }, modifier = modifier) {
        Text("Pick Image")
    }
}