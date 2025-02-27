package org.example.todotask.ui.screen.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.todotask.resources.Res
import org.example.todotask.resources.ic_sad
import org.example.todotask.resources.no_task_available
import org.example.todotask.resources.sad
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EmptyContent(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(125.dp),
            painter = painterResource(Res.drawable.ic_sad),
            contentDescription = stringResource(Res.string.sad),
            tint = Color.LightGray)
        Text(text = stringResource(Res.string.no_task_available),
            color = Color.LightGray,
            fontWeight = FontWeight.Bold)
    }

}

@Composable
@Preview
fun EmptyContentPreview(){
    EmptyContent()
}