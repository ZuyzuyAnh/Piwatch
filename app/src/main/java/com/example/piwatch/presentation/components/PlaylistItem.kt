package com.example.piwatch.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.piwatch.R
import com.example.piwatch.domain.model.PlayList

@Composable
fun PlayListItem(
    playList: PlayList,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    deletePlayList: () -> Unit
) {
    Column(
        modifier = modifier.clickable {
            onClicked()
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.wrapContentSize()
        ) {
            if (playList.playListImg != null) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500" + playList.playListImg,
                    contentDescription = null,
                    modifier = imageModifier
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.holder),
                    contentDescription = null,
                    modifier = imageModifier.clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                )
            }
            if (playList.playListName != "Favorite") {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.TopEnd)
                        .clip(RoundedCornerShape(5.dp))
                        .background(color = MaterialTheme.colorScheme.error)
                        .padding(5.dp)
                        .clickable {
                            deletePlayList()
                        },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onError,
                        modifier = Modifier.size(10.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
        Text(
            text = playList.playListName!!,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Preview
@Composable
fun PlayListItemPrv() {
    Box(
        modifier = Modifier.wrapContentSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.holder),
            contentDescription = null,
            modifier = Modifier,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
        )
    }
}