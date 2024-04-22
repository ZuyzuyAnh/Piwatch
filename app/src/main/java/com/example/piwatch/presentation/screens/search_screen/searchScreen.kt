package com.example.piwatch.presentation.screens.search_screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.piwatch.R
import com.example.piwatch.presentation.components.HeadingTextComponent
import com.example.piwatch.presentation.components.MovieItem
import com.example.piwatch.presentation.components.MyIconTextField
import kotlinx.coroutines.launch

@Composable
fun searchScreen(
    navigateToMovieDetail: (Int) -> Unit,
    navigateToHomeScreen: () -> Unit,
    navigateToLibraryScreen: () -> Unit,
    viewModel: SearchScreenViewModel?
) {
    val query = viewModel!!.query.collectAsState().value
    val state = viewModel!!.searchScrenState.collectAsState().value
    val coroutineScope = rememberCoroutineScope()
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
                    selected = true,
                    onClick = {},
                    icon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {navigateToLibraryScreen()},
                    icon = { Icon(imageVector = Icons.Outlined.VideoLibrary, contentDescription = null) }
                )
            }
        },
    ){
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp, 15.dp, 15.dp, bottom = it.calculateBottomPadding())
        ){
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    MyIconTextField(
                        value = query,
                        label = stringResource(id = R.string.search),
                        icon = Icons.Outlined.Search,
                        onTextChange = {
                            coroutineScope.launch {
                                viewModel.onEvent(SearchScreenEvent.QueryChange(it))
                            }
                        },
                        isError = false,
                        modifier = Modifier.background(MaterialTheme.colorScheme.background)
                    )
                }
                if(state.isLoading == true){
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator()
                    }
                }
                if(state.isLoading == false){
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        if(state.isError == true){
                            HeadingTextComponent(stringResource(id = R.string.no_result))
                        }else if(state.movieList.isNotEmpty()){
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ){
                                items(state.movieList){movie ->
                                    MovieItem(
                                        movie = movie,
                                        navigateToMovieDetail = {
                                            navigateToMovieDetail(it)
                                        },
                                    )
                                }
                                if(viewModel.page == 1){
                                    item {
                                        Spacer(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .requiredHeight(0.dp)
                                                .weight(1f)
                                        )
                                    }
                                    item {
                                        IconButton(
                                            onClick = {
                                                coroutineScope.launch {
                                                    viewModel.onEvent(SearchScreenEvent.nextPage)
                                                }
                                            },
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth()
                                                .background(color = MaterialTheme.colorScheme.primary)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowForward,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onPrimary
                                            )
                                        }
                                    }
                                }else{
                                    item {
                                        IconButton(
                                            onClick = {
                                                coroutineScope.launch {
                                                    viewModel.onEvent(SearchScreenEvent.previousPage)
                                                }
                                            },
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth()
                                                .background(color = MaterialTheme.colorScheme.primary)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowBack,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onPrimary
                                            )
                                        }
                                    }
                                    item {
                                        IconButton(
                                            onClick = {
                                                coroutineScope.launch {
                                                    viewModel.onEvent(SearchScreenEvent.nextPage)
                                                }
                                            },
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth()
                                                .background(color = MaterialTheme.colorScheme.primary)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowForward,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onPrimary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

