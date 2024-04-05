package com.example.piwatch.presentation.screens.library_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.piwatch.presentation.components.HeadingTextComponent
import com.example.piwatch.presentation.components.Tag
import com.example.piwatch.presentation.components.TitleTextComponent
import com.example.piwatch.ui.theme.PiWatchTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun libraryScreen(
    navigateToHomeScreen: () -> Unit,
    navigateToSearchScreen: () -> Unit,
    viewModel: LibraryViewModel
) {
    val state = viewModel.libraryState.collectAsState().value
    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.height(50.dp),
                containerColor = Color.Transparent,
                contentColor = Color.White
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = {navigateToHomeScreen()},
                    icon = { Icon(imageVector = Icons.Outlined.Home, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {navigateToSearchScreen()},
                    icon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(imageVector = Icons.Outlined.VideoLibrary, contentDescription = null) }
                )
            }
        },
        floatingActionButton = {
            Button(
                onClick = {
                    viewModel.onBuyClick()
                },
                shape = ButtonDefaults.shape
                ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ){
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp, 15.dp, 15.dp, bottom = it.calculateBottomPadding())
        ){
            if (viewModel.isDialogShown == true){
                CustomDialog(
                    onDismiss = { viewModel.onDismissDialog() },
                    playListName = viewModel.playListName,
                    onPlayListNameChange = { viewModel.onValueChange(it) },
                    addNewPlayList = {}
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                TitleTextComponent(text = "Genres")
                Spacer(modifier = Modifier.height(20.dp))
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    state.genres.map {
                        Tag(text = it.name, navigateToTag = {})
                    }
                }
                HeadingTextComponent(text = "")
            }
        }
    }

}