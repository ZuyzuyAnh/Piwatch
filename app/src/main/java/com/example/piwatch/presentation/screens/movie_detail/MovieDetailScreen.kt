package com.example.piwatch.presentation.screens.movie_detail

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.piwatch.domain.model.Movie
import com.example.piwatch.presentation.components.AddMovieToPlaylistDialog
import com.example.piwatch.presentation.components.ContentTextComponent
import com.example.piwatch.presentation.components.HeadingTextComponent
import com.example.piwatch.presentation.components.MovieRow
import com.example.piwatch.presentation.components.ParagraphContentTextComponent
import com.example.piwatch.presentation.components.RatingDialog
import com.example.piwatch.presentation.components.Tag
import com.example.piwatch.presentation.components.TitleTextComponent
import com.example.piwatch.presentation.components.primaryButton
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel,
    navigateToMovieDetail: (Int) -> Unit,
    navigateToMovieWithGenre: (Int) -> Unit,
    navigateToHomeScreen: () -> Unit,
    navigateToSearchScreen: () -> Unit,
    navigateToLibraryScreen: () -> Unit
) {
    val state = viewModel.movieDetailState.collectAsState().value
    val scrollState = rememberScrollState()
    val context = LocalContext.current
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
                    onClick = { navigateToHomeScreen() },
                    icon = { Icon(imageVector = Icons.Outlined.Home, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navigateToSearchScreen() },
                    icon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navigateToLibraryScreen() },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.VideoLibrary,
                            contentDescription = null
                        )
                    }
                )
            }
        },
    ) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = it.calculateBottomPadding())
    ){
        if(state.isLoading){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        } else {
            if (viewModel.isDialogShown == true) {
                AddMovieToPlaylistDialog(
                    onDismiss = { viewModel.onDismissDialog() },
                    playLists = state.playList,
                    addToPlaylist = {
                        coroutineScope.launch(IO) {
                            viewModel.addMovieToPlaylist(it)
                        }
                    },
                    removeFromPlaylist = {
                        coroutineScope.launch(IO) {
                            viewModel.removeMovieFromPlaylist(it)
                        }
                    },
                    movie = Movie(
                        id = state.movie.id.toLong(),
                        posterPath = state.movie.posterPath,
                        title = state.movie.title,
                        voteAverage = state.movie.voteAverage,
                    )
                )
            }
            if (viewModel.isRatingShown == true) {
                RatingDialog(
                    onDismiss = { viewModel.onDismissRating() },
                    addRating = {
                        viewModel.addRating(it)
                    }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
            ) {
                if(state.trailerKey == null){
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500" + state.movie.backdropPath,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }else{
                    YoutubePlayer(
                        state.trailerKey,
                        lifecycleOwner = LocalLifecycleOwner.current
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    TitleTextComponent(text = state.movie.title!!)
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(2f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ContentTextComponent(text = state.movie.releaseDate!!)
                            Spacer(modifier = Modifier.width(10.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Row(
                                modifier = Modifier.clickable {
                                    viewModel.onRatingClick()
                                },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color.Yellow
                                )
                                ContentTextComponent(text = state.movie.voteAverage.toString())
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        state.movie.genres.map {
                            Tag(
                                text = it.name,
                                navigateToTag = { navigateToMovieWithGenre(it.id) },
                                color = MaterialTheme.colorScheme.secondary,
                                textColor = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        primaryButton(
                            text = "Play",
                            color = MaterialTheme.colorScheme.primary,
                            icon = Icons.Default.PlayArrow,
                            onClick = {
                                viewModel.addMovieToHistory()
                                val urlIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://vidsrc.xyz/embed/movie/" + state.movie.imdbId)
                                )
                                context.startActivity(urlIntent)
                            },
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        primaryButton(
                            text = "Add to playlist",
                            color = MaterialTheme.colorScheme.tertiary,
                            icon = Icons.Default.Add,
                            onClick = {
                                viewModel.onBuyClick()
                            },
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        if(state.isFavorite){
                            Box(
                                modifier = Modifier.clickable {
                                    viewModel.onRemoveFavorite()
                                }
                            ){
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(30.dp),
                                    tint = Color.Red

                                )
                            }
                        }else{
                            Box(
                                modifier = Modifier.clickable {
                                    viewModel.onAddFavorite()
                                }
                            ){
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(30.dp),
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    HeadingTextComponent(text = "Overview", weight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    ParagraphContentTextComponent(text = state.movie.overview!!)
                    Spacer(modifier = Modifier.height(20.dp))
                    HeadingTextComponent(text = "You may like", weight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    MovieRow(movieList = state.similarMovies, navigateToMovieDetail = {
                        Log.d("navigate to others movie", "$it")
                        navigateToMovieDetail(it)
                    })
                }
            }
        }
    }
    }
    LaunchedEffect(state.isRatingSuccess) {
        if (state.shouldShowToast) {
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            viewModel.turnOffToast()
        }
    }
    LaunchedEffect(state.isAddingLoading) {
        if (state.shouldShowToast) {
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            viewModel.turnOffToast()
        }
    }
}
