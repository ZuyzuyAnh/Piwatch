package com.example.piwatch.presentation.screens.home_screen

import android.util.Log
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.piwatch.data.repositoryImpl.AuthEvent
import com.example.piwatch.presentation.Navigation.AppRoute
import com.example.piwatch.presentation.components.HeadingMovieRow
import com.example.piwatch.presentation.components.HeadingTextComponent
import com.example.piwatch.presentation.components.MovieItem
import com.example.piwatch.presentation.components.MovieRow
import com.example.piwatch.presentation.components.PiWatchLogo
import com.example.piwatch.presentation.components.TextComponent
import com.example.piwatch.presentation.screens.auth_state.AuthViewModel
import com.example.piwatch.ui.theme.PiWatchTheme
import dagger.Provides
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun HomeScreen(
    navigateToMovieDetail: (Int) -> Unit,
    navigateToSearchScreen: () -> Unit,
    navigateToLibraryScreen: () -> Unit,
    viewModel: HomeViewModel
) {
    val state = viewModel.homeState.collectAsState().value
    val scrollState = rememberScrollState()
    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.height(50.dp),
                containerColor = Color.Transparent,
                contentColor = Color.White
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(imageVector = Icons.Outlined.Home, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {navigateToSearchScreen()},
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
                .padding(15.dp, 15.dp, 15.dp, bottom = it.calculateBottomPadding()),

            ) {
            if(state.upcomingMovies.isEmpty()){
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            }else{
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PiWatchLogo(
                            modifier = Modifier.height(30.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Enjoy your show",
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Column {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            HeadingTextComponent(text = "Upcoming")
                            Spacer(modifier = Modifier.height(10.dp))
                            HeadingMovieRow(state.upcomingMovies)
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        TextComponent(
                            text = "Popular Movies"
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        MovieRow(movieList = state.popularMovies) {
                            navigateToMovieDetail(it)
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        TextComponent(
                            text = "Top Rated Movies"
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        MovieRow(movieList = state.topRatedMovies) {
                            navigateToMovieDetail(it)
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

