package org.example.todotask.ui.screen.list



import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import org.example.todotask.ui.theme.LARGE_PADDING
import org.example.todotask.ui.theme.TOP_APP_BAR_SIZE
import org.example.todotask.util.Actions
import org.example.todotask.util.SearchAppBarState
import org.example.todotask.PriorityItem
import org.example.todotask.components.DisplayAlertDialog
import org.example.todotask.data.models.Priority
import org.example.todotask.resources.Res
import org.example.todotask.resources.close_icon
import org.example.todotask.resources.ic_delete_all
import org.example.todotask.resources.ic_filter_24
import org.example.todotask.resources.list_screen
import org.example.todotask.resources.remove_all_task_text
import org.example.todotask.resources.remove_all_task_title
import org.example.todotask.resources.search_task
import org.example.todotask.viewmodel.SharedViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ListAppBar(
    sharedViewModel : SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState:String,
    onSearchClicked: (String) -> Unit
) {
    when(searchAppBarState){
        SearchAppBarState.CLOSED -> {
            DefaultAppBar(
                onSearchClicked = {
                    sharedViewModel.updateAppBarState(SearchAppBarState.OPENED)
                },
                onSortClicked = { priority ->
                                sharedViewModel.persistSortState(priority)
                },
                onDeleteClicked = {
                    sharedViewModel.updateAction(Actions.DELETE_ALL)
                }
            )
        }
        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = {
                    sharedViewModel.updateSearchText(newText = it)
                },
                onCloseClick = {
                    sharedViewModel.updateAppBarState(SearchAppBarState.CLOSED)
                    sharedViewModel.updateSearchText(newText = "")
                },
                onSearchClicked = { searchText ->
                    onSearchClicked(searchText)
                }
            )
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(onSearchClicked:() -> Unit,
                  onSortClicked:(Priority) -> Unit,
                  onDeleteClicked:() -> Unit){
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        title = { Text(text = stringResource(Res.string.list_screen))},
        actions = {
            ListAppBarActions(
                onSearchClicked,
                onSortClicked,
                onDeleteClicked
            )
        }
    )
}


@Composable
fun ListAppBarActions( onSearchClicked:() -> Unit,
                       onSortClicked:(Priority) -> Unit,
                       onDeleteAllClicked:() -> Unit){
    SearchAction(onSearchClicked)
    SortAction(onSortClicked)
    DeleteAll(onDeleteAllClicked)
}

@Composable
fun SearchAction(
    onSearchClicked:() -> Unit
){
    IconButton(onClick = { onSearchClicked()  }) {
        Icon(imageVector = Icons.Filled.Search, contentDescription =  stringResource(Res.string.search_task),
            tint = MaterialTheme.colorScheme.surfaceTint
        )
    }
}

@Composable
fun SortAction(
    onSortClicked:(Priority) -> Unit
){
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true  }) {
        Icon(painter = painterResource(Res.drawable.ic_filter_24), contentDescription = stringResource(Res.string.search_task),
            tint = MaterialTheme.colorScheme.surfaceTint
        )
        DropdownMenu(expanded = expanded,
            onDismissRequest = { expanded = false }) {

            Priority.entries.toTypedArray().slice(setOf(0,2,3)).forEach { priority ->
                DropdownMenuItem(text = {
                    PriorityItem(priority = priority)
                }, onClick = {
                    expanded = false
                    onSortClicked(priority)
                })
            }
        }
    }
}

@Composable
fun DeleteAll(
    deleteAllClicked:() -> Unit
){

    var expand : Boolean by remember { mutableStateOf(false) }
    var openDialog:Boolean by remember { mutableStateOf(false) }

    DisplayAlertDialog(
        title = stringResource(Res.string.remove_all_task_title),
        text = stringResource(Res.string.remove_all_task_text),
        isDialogOpen = openDialog,
        onDismissClicked = {
            openDialog = false
        },
        onConfirmClicked = {
            deleteAllClicked()
        }
    )

    IconButton(onClick = {
        expand = true
    }) {
        Icon(
            painter = painterResource(Res.drawable.ic_delete_all) ,
            contentDescription = "Delete all", tint = MaterialTheme.colorScheme.surfaceTint
        )
        DropdownMenu(
            expanded = expand,
            onDismissRequest = {
                expand = false
            }
        ) {
            DropdownMenuItem(
                text = { Text(
                    text = "Delete All",
                    modifier = Modifier.padding(start = LARGE_PADDING),
                    style = MaterialTheme.typography.labelMedium
                ) },
                onClick = {
                    expand = false
                    openDialog = true
                }
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    text:String,
    onTextChange:(String) -> Unit,
    onCloseClick:() -> Unit,
    onSearchClicked: (String) -> Unit
){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_SIZE),
        color = MaterialTheme.colorScheme.primary
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = { Text(modifier = Modifier.alpha(0.5f),
                text = "Search",color = Color.Gray)},
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.titleMedium.fontSize),
            singleLine = true,
            leadingIcon = { 
                IconButton(modifier = Modifier.alpha(0.2f),
                    onClick = {  }) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Task",
                        tint = MaterialTheme.colorScheme.surfaceTint
                    )
                }
            },
            trailingIcon = {
                IconButton(modifier = Modifier.alpha(0.2f),
                    onClick = {
                        if (text.isNotEmpty()){
                            onTextChange("")
                        }else{
                            onCloseClick()
                        }
                    }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = stringResource(Res.string.close_icon),
                        tint = MaterialTheme.colorScheme.surfaceTint
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Preview
@Composable
fun DefaultAppBarPreview(){
    DefaultAppBar(
        onSearchClicked = {},
        onSortClicked = {},
        onDeleteClicked = {})
}


@Preview
@Composable
fun SearchAppBarPreview(){
    SearchAppBar(text = "Search Value",
        onTextChange = {},
        onSearchClicked = {},
        onCloseClick = {})
}