package org.example.todotask

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun CaptureImageButton(
    controller: CameraController,
    onCaptured: (String?) -> Unit,
    modifier: Modifier = Modifier
)