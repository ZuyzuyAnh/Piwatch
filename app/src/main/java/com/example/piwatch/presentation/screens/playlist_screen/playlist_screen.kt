package com.example.piwatch.presentation.screens.playlist_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.piwatch.domain.model.Movie
import com.example.piwatch.presentation.components.CustomTopBar
import com.example.piwatch.presentation.components.MovieColumn


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayList(
    navigateToLibrary: () -> Unit,
    navigateToMovieDetail: (Int) -> Unit,
    viewModel: PlaylistViewModel
) {
    val state = viewModel.playlistState.collectAsState().value
    val context = LocalContext.current
    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    CustomTopBar(navigateToLibrary, title = state.playList.playListName!!)
                })
            }
        ) {
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding(), start = 5.dp, end = 5.dp)
            ) {
                PlaylistScreen(
                    navigateToMovieDetail = navigateToMovieDetail,
                    movies = state.playList.movieList!!,
                    removeMovieFromPlaylist = { viewModel.removeMovieFromPlaylist(it) }
                )
            }
        }
    }
    LaunchedEffect(state.isRemoveSuccess) {
        if (state.shouldShowToast) {
            if (state.isRemoveSuccess) {
                Toast.makeText(context, state.toast!!, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, state.errorToast!!, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.turnOffToast()
    }
}

@Composable
fun PlaylistScreen(
    navigateToMovieDetail: (Int) -> Unit,
    movies: List<Movie>,
    removeMovieFromPlaylist: (Movie) -> Unit
) {
    MovieColumn(
        movies = movies,
        navigateToMovieDetail = navigateToMovieDetail,
        removeMovieFromPlaylist = { removeMovieFromPlaylist(it) }
    )
}

