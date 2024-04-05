package com.example.piwatch.presentation.screens.movie_detail

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.piwatch.presentation.components.ContentTextComponent
import com.example.piwatch.presentation.components.HeadingTextComponent
import com.example.piwatch.presentation.components.LoginButton
import com.example.piwatch.presentation.components.LoginButtonWithIcon
import com.example.piwatch.presentation.components.LoginButtonWithIcon2
import com.example.piwatch.presentation.components.MovieRow
import com.example.piwatch.presentation.components.ParagraphContentTextComponent
import com.example.piwatch.presentation.components.Tag
import com.example.piwatch.presentation.components.TextComponent
import com.example.piwatch.presentation.components.TitleTextComponent
import com.example.piwatch.presentation.components.primaryButton

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel,
    navigateToMovieDetail: (Int) -> Unit
) {
    val state = viewModel.movieDetailState.collectAsState().value
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
    ){
        if(state.isLoading){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        if(state.isLoading == false){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
            ) {
                if(state.trailerKey == null){
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500" + state.backdropPath,
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
                    TitleTextComponent(text = state.title!!)
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(modifier = Modifier.weight(2f)){
                            ContentTextComponent(text = state.releaseDate!!)
                            Spacer(modifier = Modifier.width(10.dp))
                            ContentTextComponent(
                                text = state.status!!
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color.Yellow)
                            ContentTextComponent(text = state.voteAverage.toString())
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        state.genres.map {
                                it -> Tag(text = it, navigateToTag = {})
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        primaryButton(
                            text = "Play",
                            color = MaterialTheme.colorScheme.primary,
                            icon = Icons.Default.PlayArrow,
                            onClick = {
                                val urlIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://vidsrc.xyz/embed/movie/" + state.imdbId)
                                )
                                context.startActivity(urlIntent)
                            },
                        )
                    }
                    HeadingTextComponent(text = "Overview")
                    Spacer(modifier = Modifier.height(5.dp))
                    ParagraphContentTextComponent(text = state.overview!!)
                    Spacer(modifier = Modifier.height(20.dp))
                    HeadingTextComponent(text = "You may like")
                    Spacer(modifier = Modifier.height(10.dp))
                    MovieRow(movieList = state.similarMovies, navigateToMovieDetail = {
                        navigateToMovieDetail(it)
                    })
                }
            }
    }
    }
}
