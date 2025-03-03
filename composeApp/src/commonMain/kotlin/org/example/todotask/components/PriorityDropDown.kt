package org.example.todotask.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import org.example.todotask.data.models.Priority
import org.example.todotask.util.PRIORITY_DROP_DOWN_HEIGHT
import org.example.todotask.util.PRIORITY_INDICATOR_SIZE
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PriorityDropDown(
    priority: Priority,
    onPrioritySelected:(Priority) -> Unit
){

    var expanded by remember { mutableStateOf(false) }
    val angle:Float by animateFloatAsState(targetValue = if (expanded) 180f else 0f)
    var dropdownWidth by remember { mutableStateOf(IntSize.Zero) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                dropdownWidth = it.size
            }
            .background(MaterialTheme.colorScheme.background)
            .height(PRIORITY_DROP_DOWN_HEIGHT)
            .clickable {
                expanded = true
            }
            .border(
                width = 1.dp,
//                color = MaterialTheme.colorScheme.primary.copy(alpha = ContentAlpha.disabled),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.58f),
                shape = MaterialTheme.shapes.small
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = Modifier
            .size(PRIORITY_INDICATOR_SIZE)
            .weight(1f)) {
            drawCircle(color = priority.color)
        }

        Text( modifier = Modifier.weight(8f),
            text = priority.name)

        IconButton(modifier = Modifier
            .weight(1.5f)
//            .alpha(ContentAlpha.medium)
            .alpha(0.5f)
            .rotate(degrees = angle),
            onClick = { expanded = true }) {
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "arrow")

            DropdownMenu(
                modifier = Modifier.width(with(LocalDensity.current){ dropdownWidth.width.toDp() }),
                expanded = expanded ,
                onDismissRequest = { expanded= false }) {

                Priority.entries.toTypedArray().slice(0..2).forEach { priority ->
                    DropdownMenuItem(text = {
                        PriorityItem(priority = priority)
                    }, onClick = {
                        expanded = false
                        onPrioritySelected(priority)
                    })
                }
            }
        }

    }

}

@Composable
@Preview
fun PriorityDropDownPreview(){

    PriorityDropDown(Priority.LOW,{

    })
}