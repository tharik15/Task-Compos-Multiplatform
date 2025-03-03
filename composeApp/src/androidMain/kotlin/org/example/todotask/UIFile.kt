package org.example.todotask

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ContentAlpha
import kotlinx.coroutines.delay
import org.example.todotask.components.PriorityItem
import org.example.todotask.data.models.Priority
import org.example.todotask.resources.Res
import org.example.todotask.resources.logo
import org.example.todotask.util.Actions
import org.example.todotask.util.PRIORITY_DROP_DOWN_HEIGHT
import org.example.todotask.util.PRIORITY_INDICATOR_SIZE
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

//
//@Composable
//fun SplashScreen(
//    navigateListScreen:(String) -> Unit
//){
//    var isStartAnimation by remember { mutableStateOf(false) }
//    val offSetState by animateDpAsState(targetValue =
//    if (isStartAnimation) 0.dp else 100.dp,
//        animationSpec = tween(
//            durationMillis = 1000
//        )
//    )
//
//    val alphaState by animateFloatAsState(
//        targetValue = if (isStartAnimation) 1f else 0f,
//        animationSpec = tween(
//            durationMillis = 1000
//        )
//    )
//    LaunchedEffect(key1 = true) {
//        isStartAnimation = true
//        delay(3000)
//        navigateListScreen(Actions.NO_ACTION)
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .alpha(alphaState)
//            .offset(offSetState)
//            .background(MaterialTheme.colorScheme.background),
//        contentAlignment = Alignment.Center
//
//    ) {
//        Column(modifier = Modifier.padding(5.dp),
//            horizontalAlignment = Alignment.CenterHorizontally) {
//            Image(
//                painter = painterResource(getDrawable()),
//                contentDescription = "Logo")
//
//            Text(modifier = Modifier,
//                text = "Todo Task",
//                color = Color.Black,
//                fontStyle = FontStyle.Italic,
//                style = MaterialTheme.typography.titleLarge,
//                maxLines = 1)
//        }
//    }
//}
//
//@Composable
//fun getDrawable(): DrawableResource {
//    return if (isSystemInDarkTheme()){
//        Res.drawable.logo
//    }else{
//        Res.drawable.logo
//    }
//}
//
//@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
//@Composable
//fun SplashScreenPreview(){
//    SplashScreen({
//
//    })
//}

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
                color = MaterialTheme.colorScheme.primary.copy(alpha = ContentAlpha.disabled),
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
            .alpha(ContentAlpha.medium)
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



@androidx.compose.ui.tooling.preview.Preview
@Composable
fun PriorityDropDownPreview(){
    PriorityDropDown(Priority.LOW, {

    })
}