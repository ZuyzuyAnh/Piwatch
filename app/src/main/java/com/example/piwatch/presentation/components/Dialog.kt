package com.example.piwatch.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.piwatch.R
import com.example.piwatch.domain.model.Movie
import com.example.piwatch.domain.model.PlayList

@Composable
fun CustomDialog(
    onDismiss: () -> Unit,
    playListName: String,
    onPlayListNameChange: (String) -> Unit,
    addNewPlayList: () -> Unit,
    error: Boolean
) {
    Dialog(
        onDismissRequest = {onDismiss()},
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .border(1.dp, Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                HeadingTextComponent(
                    text = stringResource(id = R.string.add_playlist),
                    weight = FontWeight.Bold
                )
                MyTextField(
                    label = stringResource(id = R.string.playlist_name),
                    onValueChange = {
                        onPlayListNameChange(it)
                    },
                    value = playListName,
                    isError = error,
                )
                if (error) {
                    ValidateError(stringResource(id = R.string.duplicate_playlist))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable {
                                onDismiss()
                            }
                    ) {
                        ContentTextComponent(text = stringResource(id = R.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable {
                                if (!error && playListName.isNotEmpty()) {
                                    addNewPlayList()
                                    onDismiss()
                                }
                            }
                    ) {
                        if (playListName.isEmpty() || error) {
                            ContentTextComponent(
                                text = stringResource(id = R.string.confirm),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        } else {
                            ContentTextComponent(text = stringResource(id = R.string.confirm))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddMovieToPlaylistDialog(
    onDismiss: () -> Unit,
    playLists: List<PlayList>,
    addToPlaylist: (Int) -> Unit,
    removeFromPlaylist: (Int) -> Unit,
    movie: Movie
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .border(1.dp, Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                HeadingTextComponent(text = "Add to", weight = FontWeight.Bold)
                LazyColumn(
                    modifier = Modifier.padding(5.dp),
                ) {
                    items(items = playLists.subList(1,playLists.size)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val movieList = it.movieList
                            val isMovieInPlaylist = movieList!!.contains(movie)
                            TextComponent(text = it.playListName!!, weight = FontWeight.Bold)
                            if (isMovieInPlaylist) {
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .clickable { removeFromPlaylist(it.playListId!!) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = null
                                    )
                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .clickable {
                                            addToPlaylist(it.playListId!!)
                                        }
                                ) {
                                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RatingDialog(
    onDismiss: () -> Unit,
    addRating: (Float) -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        var sliderPosition by remember { mutableFloatStateOf(0f) }
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .border(1.dp, Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                CustomSlider(
                    addRating = {
                        addRating(it)
                    },
                    onDismiss = { onDismiss() }
                )
            }
        }
    }
}