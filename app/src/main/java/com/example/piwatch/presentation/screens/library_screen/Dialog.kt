package com.example.piwatch.presentation.screens.library_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.piwatch.presentation.components.HeadingTextComponent
import com.example.piwatch.presentation.components.MyTextField
import com.example.piwatch.presentation.components.TextComponent
import com.example.piwatch.ui.theme.PiWatchTheme

@Composable
fun CustomDialog(
    onDismiss: () -> Unit,
    playListName: String,
    onPlayListNameChange: (String) -> Unit,
    addNewPlayList: (String) -> Unit
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
                .fillMaxWidth(0.95f)
                .border(1.dp, Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                HeadingTextComponent(text = "Add new playlist")
                MyTextField(
                    label = "Playlist Name",
                    onValueChange = onPlayListNameChange,
                    value = playListName
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                        onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                    ) {
                        TextComponent(text = "Cancel", color = MaterialTheme.colorScheme.onError)
                    }
                    Button(onClick = {
                        addNewPlayList(playListName)
                        onDismiss()
                    }) {
                        TextComponent(text = "Add",color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DialogPreview() {
    PiWatchTheme {
        CustomDialog(
            {},"",{},{}
        )
    }

}