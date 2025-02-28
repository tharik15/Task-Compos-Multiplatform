package org.example.todotask.ui.screen.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.todotask.ui.theme.EX_LARGE_PADDING
import org.example.todotask.ui.theme.LARGE_PADDING
import org.example.todotask.util.Actions
import org.example.todotask.util.RequestState
import org.example.todotask.util.SearchAppBarState
import org.example.todotask.data.models.TodoTask
import org.example.todotask.data.models.Priority
import org.example.todotask.resources.Res
import org.example.todotask.resources.delete_icon
import org.example.todotask.ui.theme.HighPriorityColor
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    taskList: RequestState<List<TodoTask>>,
    searchTask: RequestState<List<TodoTask>>,
    navigateToTaskScreen :(Int) -> Unit,
    lowPriorityTask:List<TodoTask>,
    highPriorityTask:List<TodoTask>,
    sortState: RequestState<Priority>,
    searchAppBarState: SearchAppBarState,
    swipeToDelete:(String, TodoTask) -> Unit
){
    if (sortState is RequestState.Success){
        when{
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchTask is RequestState.Success){
                    HandleListContent(modifier = modifier,
                        taskList = searchTask.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        swipeToDelete = swipeToDelete)
                }
            }
            sortState.data == Priority.NONE -> {

                if (taskList is RequestState.Success){
                    HandleListContent(modifier = modifier,
                        taskList = taskList.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        swipeToDelete = swipeToDelete)
                }
            }
            sortState.data == Priority.LOW -> {
                HandleListContent(modifier = modifier,
                    taskList = lowPriorityTask,
                    navigateToTaskScreen = navigateToTaskScreen,
                    swipeToDelete = swipeToDelete)
            }
            sortState.data == Priority.HIGH -> {
                HandleListContent(modifier = modifier,
                    taskList = highPriorityTask,
                    navigateToTaskScreen = navigateToTaskScreen,
                    swipeToDelete = swipeToDelete)
            }
        }

    }
}

@Composable
fun HandleListContent(
    modifier: Modifier = Modifier,
    taskList:List<TodoTask>,
    navigateToTaskScreen :(Int) -> Unit,
    swipeToDelete:(String, TodoTask) -> Unit
){
    if (taskList.isEmpty()){
        EmptyContent()
    }else{
        ListContentView(modifier = modifier,
            taskList = taskList,
            navigateToTaskScreen = navigateToTaskScreen,
            swipeToDelete = swipeToDelete)
    }
}

@Composable
fun ListContentView(modifier: Modifier = Modifier,
                    taskList: List<TodoTask>,
                    navigateToTaskScreen :(Int) -> Unit,
                    swipeToDelete:(String, TodoTask) -> Unit){

    LazyColumn (modifier = modifier.fillMaxSize(),
        state = rememberLazyListState()){
        items(
            items = taskList,
            key ={task -> task.id}

        ){todoTask ->
//            val dismissState = rememberDismissState()
//            val angle:Float by animateFloatAsState(targetValue = if (dismissState.progress in 0f..0.5f) 0f else -45f)
//            val directions = dismissState.dismissDirection
//            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart) && dismissState.progress == 1f
//            if (isDismissed && directions == DismissDirection.EndToStart){
//                val scope = rememberCoroutineScope()
//                LaunchedEffect(key1 = true) {
//                    scope.launch {
//                        delay(300)
//                        swipeToDelete(Actions.DELETE,todoTask)
//                    }
//                }
//
//            }

            var isAnimated by remember { mutableStateOf(false) }
            LaunchedEffect(key1 = true) {
                isAnimated = true
            }
            AnimatedVisibility(
                visible = isAnimated /*&& !isDismissed*/,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = 300)
                ),
                exit = shrinkVertically(
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                DisplayTaskElevatedCard(todoTask,navigateToTaskScreen)

//                SwipeToDismiss(
//                    state = dismissState,
//                    background = { RedBackground(rotate = angle)},
//                    directions = setOf(DismissDirection.EndToStart),
//                    dismissContent = {
//                        DisplayTaskElevatedCard(todoTask,navigateToTaskScreen)
//                    }
//                )
            }

        }
    }
}

@Composable
fun RedBackground(rotate:Float){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 15.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Box (modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(HighPriorityColor)
                .padding(horizontal = EX_LARGE_PADDING)){
        Icon(modifier = Modifier
            .rotate(rotate)
            .align(Alignment.CenterEnd),
            imageVector = Icons.Filled.Delete, contentDescription = stringResource(Res.string.delete_icon),
            tint = Color.White)
    }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayTaskElevatedCard(
    todoTask: TodoTask,
    navigateToTaskScreen :(taskId:Int) -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 15.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        onClick = {
            navigateToTaskScreen(todoTask.id)
        }
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(LARGE_PADDING),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom) {
                Text(
                    modifier = Modifier
                        .weight(8f),
                    text = todoTask.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1)
                Box(contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier.weight(2f)
                ) {
                    Canvas(modifier = Modifier
                        .height(LARGE_PADDING)
                        .width(LARGE_PADDING)) {
                        drawCircle(color = todoTask.priority.color)

                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = todoTask.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2)

        }
    }
}

@Composable
@Preview
fun RedComposablePreview(){
    RedBackground(-25f)
}

@Composable
@Preview
fun ElevatedCardExamplePreview() {
    DisplayTaskElevatedCard(todoTask = TodoTask(1,
        "Test Title","Content value",Priority.HIGH),
        {})
}