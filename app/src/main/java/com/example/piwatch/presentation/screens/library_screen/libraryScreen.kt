package com.example.piwatch.presentation.screens.library_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.piwatch.R
import com.example.piwatch.presentation.components.CustomDialog
import com.example.piwatch.presentation.components.HeadingTextComponent
import com.example.piwatch.presentation.components.MovieRow
import com.example.piwatch.presentation.components.PlaylistRow
import com.example.piwatch.presentation.components.Tag
import com.example.piwatch.presentation.components.TitleTextComponent
import com.example.piwatch.presentation.components.iconWithText
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LibraryScreen(
    navigateToHomeScreen: () -> Unit,
    navigateToSearchScreen: () -> Unit,
    navigateToMovieDetail: (Int) -> Unit,
    navigateToPlaylist: (Int, String) -> Unit,
    navigateToMovieWithGenre: (Int) -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: LibraryViewModel
) {
    val state = viewModel.libraryState.collectAsState().value
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.swipRefresh)
    val context = LocalContext.current
    Log.d("library state", "$state")
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
            FloatingActionButton(
                onClick = {
                    viewModel.onBuyClick()
                },
                containerColor = MaterialTheme.colorScheme.primary
                ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { it ->
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                coroutineScope.launch(IO) {
                    viewModel.loadUserHistory()
                }
            }
        ) {
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp, 15.dp, 15.dp, bottom = it.calculateBottomPadding())
            ) {
                if (viewModel.isDialogShown) {
                    CustomDialog(
                        onDismiss = { viewModel.onDismissDialog() },
                        playListName = viewModel.playListName,
                        onPlayListNameChange = { viewModel.onValueChange(it) },
                        addNewPlayList = {
                            coroutineScope.launch {
                                viewModel.addNewPlayList()
                            }
                        },
                        error = state.error
                    )
                }
                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(scrollState)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TitleTextComponent(text = stringResource(id = R.string.genres))
                            Spacer(modifier = Modifier)
                            Profile(
                                signOut = {
                                    viewModel.signOut()
                                    navigateToLogin()
                                },
                                userName = state.userName!!
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            state.genres.map { genre ->
                                Tag(
                                    text = genre.name,
                                    navigateToTag = { navigateToMovieWithGenre(genre.id) },
                                    color = MaterialTheme.colorScheme.tertiary,
                                    textColor = MaterialTheme.colorScheme.onTertiary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        HeadingTextComponent(
                            text = stringResource(id = R.string.playlist),
                            weight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        PlaylistRow(
                            playLists = state.playList,
                            onItemClick = navigateToPlaylist,
                            userId = viewModel.userId!!,
                            deletePlayList = {
                                viewModel.deletePlaylist(it)
                            },
                            isLoading = state.isAddPlaylistLoading
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        HeadingTextComponent(
                            text = stringResource(id = R.string.history),
                            weight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        MovieRow(
                            movieList = state.history.reversed(),
                            navigateToMovieDetail = { navigateToMovieDetail(it) })

                    }
                }
            }
        }
    }
    LaunchedEffect(state.isAddingSuccess) {
        if (state.shouldShowToast) {
            if (state.isAddingSuccess) {
                Toast.makeText(context, state.toast!!, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, state.errorToast!!, Toast.LENGTH_SHORT).show()
            }
            viewModel.turnOffToast()
        }
    }

    LaunchedEffect(state.isRemoveSuccess) {
        if (state.shouldShowToast) {
            if (state.isRemoveSuccess) {
                Toast.makeText(context, state.toast!!, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, state.errorToast!!, Toast.LENGTH_SHORT).show()
            }
            viewModel.turnOffToast()
        }
    }


}

@Composable
fun Profile(
    signOut: () -> Unit,
    userName: String
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background, CircleShape)
            .border(1.dp, Color.Black, CircleShape)
            .padding(5.dp)
            .clickable {
                expanded = !expanded
            }
    ) {
        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = null,
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { iconWithText(text = userName, icon = Icons.Outlined.Person) },
                onClick = {}
            )
            DropdownMenuItem(
                text = { iconWithText(text = "Sign out", icon = Icons.Outlined.ExitToApp) },
                onClick = { signOut() }
            )
        }
    }
}