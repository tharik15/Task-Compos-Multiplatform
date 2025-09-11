package org.example.todotask

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File

@Composable
actual fun CaptureImageButton(
    controller: CameraController,
    onCaptured: (String?) -> Unit,
    modifier: Modifier
) {
    val context = LocalContext.current
    var pendingFile by remember { mutableStateOf<File?>(null) }

    val takePicture = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        onCaptured(if (success) pendingFile?.absolutePath else null)
    }

    fun capture() {
        val photosDir = File(context.filesDir, "photos").apply { mkdirs() }
        val file = File(photosDir, "evidence_${System.currentTimeMillis()}.jpg")
        pendingFile = file
        val uri: Uri = FileProvider.getUriForFile(
            context, "${context.packageName}.provider", file
        )
        takePicture.launch(uri)
    }

    val requestPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            capture()
        }
    }

    Button(
        onClick = {
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                capture()
            } else {
                requestPermission.launch(Manifest.permission.CAMERA)
            }
        },
        modifier = modifier
    ) {
        Text("Capture Photo")
    }
}