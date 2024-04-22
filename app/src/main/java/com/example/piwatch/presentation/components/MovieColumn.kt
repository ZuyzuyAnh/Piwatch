package com.example.piwatch.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.piwatch.domain.model.Movie

@Composable
fun MovieColumn(
    movies: List<Movie>,
    navigateToMovieDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    removeMovieFromPlaylist: (Movie) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(movies) { movie ->
            MovieColumnItem(
                modifier = modifier,
                movie = movie,
                navigateToMovieDetail = { navigateToMovieDetail(it) },
                removeMovieFromPlaylist = { removeMovieFromPlaylist(it) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun MovieColumnItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    navigateToMovieDetail: (Int) -> Unit,
    removeMovieFromPlaylist: (Movie) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .weight(1f)
                .clickable { navigateToMovieDetail(movie.id!!.toInt()) }
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500" + movie.posterPath,
                contentDescription = null,
                modifier = modifier
                    .height(120.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
            )
        }
        Box(modifier = modifier.weight(2f)) {
            Column(
                modifier = modifier.fillMaxWidth()
            ) {
                TextComponent(text = movie.title!!, weight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Yellow
                    )
                    ContentTextComponent(text = movie.voteAverage.toString())
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = modifier.clickable {
                    removeMovieFromPlaylist(movie)
                }) {
                    Icon(imageVector = Icons.Outlined.DeleteOutline, contentDescription = null)
                }
            }
        }

    }
}
