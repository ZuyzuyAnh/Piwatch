package com.example.piwatch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.piwatch.domain.model.Movie
import com.example.piwatch.ui.theme.PiWatchTheme

@Composable
fun MovieItem(
    movie: Movie,
    navigateToMovieDetail: (Int) -> Unit,
    imageModifier: Modifier = Modifier,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable {
            navigateToMovieDetail(movie.id!!.toInt())
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ){
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500" + movie.posterPath,
                contentDescription = null,
                modifier = imageModifier
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = Crop,
                alignment = Alignment.Center,
            )
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = MaterialTheme.colorScheme.surfaceTint)
                    .padding(5.dp),
            ){
                Text(
                    text = "${Math.round(movie.voteAverage!! * 10.0) / 10.0}/10",
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light
                    ),
                    color = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier
                )
            }


        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = movie.title!!,
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,

        )
    }
}
@Preview(showBackground = true)
@Composable
fun MovieItemPrv(
){
    PiWatchTheme {
        MovieItem(
            movie = Movie(
                id = 1011985,
                posterPath = "/kDp1vUBnMpe8ak4rjgl3cLELqjU.jpg",
                title = "Kung Fu Panda 4",
                voteAverage = 6.986,
            ),
            {}
        )
    }
}