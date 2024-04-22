package com.example.piwatch.presentation.screens.movie_with_genre

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.piwatch.presentation.components.MovieItem
import com.example.piwatch.presentation.components.TitleTextComponent
import kotlinx.coroutines.launch

@Composable
fun movieWithGenre(
    navigateToMovieDetail: (Int) -> Unit,
    viewModel: MovieWithGenreViewModel
) {
    val state = viewModel.movieWithGenreState.collectAsState().value
    val coroutineScope = rememberCoroutineScope()
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp, 15.dp, 15.dp, bottom = 15.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.isLoading == true || state.genre == null) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TitleTextComponent(text = state.genre!!)
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.movies) { movie ->
                            MovieItem(
                                movie = movie,
                                navigateToMovieDetail = {
                                    navigateToMovieDetail(it)
                                },
                            )
                        }
                        if (viewModel.page == 1) {
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
                                            viewModel.onEvent(MovieWithGenreEvent.nextPage)
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
                        } else {
                            item {
                                IconButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            viewModel.onEvent(MovieWithGenreEvent.previousPage)
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
                                            viewModel.onEvent(MovieWithGenreEvent.nextPage)
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