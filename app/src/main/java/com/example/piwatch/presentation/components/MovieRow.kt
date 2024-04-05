package com.example.piwatch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.piwatch.domain.model.Movie

@Composable
fun MovieRow(
    movieList: List<Movie>,
    navigateToMovieDetail: (Int) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 10.dp),
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primaryContainer).clip(
            RoundedCornerShape(20.dp)
        ),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ){
        items(items = movieList){
            MovieItem(
                movie = it,
                navigateToMovieDetail,
                imageModifier = Modifier.height(150.dp)
                    .width(100.dp),
                modifier = Modifier.width(100.dp)
            )
        }
    }

}