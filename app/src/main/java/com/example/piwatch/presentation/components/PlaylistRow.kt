package com.example.piwatch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.piwatch.domain.model.PlayList

@Composable
fun PlaylistRow(
    playLists: List<PlayList>,
    onItemClick: (Int, String) -> Unit,
    userId: String,
    deletePlayList: (Int) -> Unit,
    isLoading: Boolean
) {
    LazyRow(
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clip(
                RoundedCornerShape(20.dp)
            ),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(playLists.size) { index ->
            val playlist = playLists[index]
            PlayListItem(
                playList = playlist,
                onClicked = { onItemClick(playlist.playListId!!, userId) },
                imageModifier = Modifier
                    .height(150.dp)
                    .width(100.dp),
                modifier = Modifier.width(100.dp),
                deletePlayList = {
                    deletePlayList(playlist.playListId!!)
                }
            )
        }
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}