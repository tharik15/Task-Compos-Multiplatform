package org.example.todotask.ui.screen.task

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.todotask.data.models.TodoTask

import org.example.todotask.util.Actions
import org.example.todotask.data.models.Priority
import org.example.todotask.viewmodel.SharedViewModel

@Composable
fun TaskScreen(
    todoTask: TodoTask?,
    sharedViewModel: SharedViewModel,
    navigateListTaskScreen:(String) -> Unit
){
    val title:String = sharedViewModel.title
    val description:String = sharedViewModel.description
    val priority: Priority = sharedViewModel.priority
//    BackHandler {
//        navigateListTaskScreen(Actions.NO_ACTION)
//    }
    Scaffold(
        topBar = {
              TaskAppBar(todoTask,
                  sharedViewModel,
                  navigateListTaskScreen = { actions ->
                      if (actions == Actions.NO_ACTION){
                          navigateListTaskScreen(actions)
                      }else{
                          if (sharedViewModel.isValidation()){
                              navigateListTaskScreen(actions)
                          }else {
//                              showMessage()
                          }
                      }
                  })
        },
        content = {
            TaskContent(
                modifier = Modifier.padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                ),
                title = title,
                onTitleChanges = { title ->
                    sharedViewModel.updateTitleByLength(title)
                },
                priority,
                onPrioritySelected = { priority ->
                    sharedViewModel.updatePriority(newPriority = priority)
                },
                description,
                onDescriptionChanged = {desc ->
                    sharedViewModel.updateDescription(desc)
                }
            )
        }
    )
}

//@Composable
//fun showMessage() {
//    val scope = rememberCoroutineScope()
//    scope.launch {
//        ShowToast("Hello, World!")
//    }
//}
/*@Composable
fun BackHandler(
    backDispatcher: OnBackPressedDispatcher? = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed:() -> Unit
){

    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }

        }
    }
    DisposableEffect(key1 = backDispatcher) {
        backDispatcher?.addCallback(backCallback)
        onDispose{
            backCallback.remove()
        }
    }
}*/
