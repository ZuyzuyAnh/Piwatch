package com.example.piwatch.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight


@Composable
fun CustomSlider(
    value: Float = 0f,
    addRating: (Float) -> Unit,
    onDismiss: () -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(value) }
    Column {
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = Color.Yellow,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            steps = 17,
            valueRange = 1f..10f
        )
        TextComponent(text = String.format("%.1f", sliderPosition), weight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    Log.d("inspect rating added", "${sliderPosition}")
                    addRating(sliderPosition)
                    onDismiss()
                }) {
                TextComponent(
                    text = "OK",
                    color = MaterialTheme.colorScheme.onPrimary,
                    weight = FontWeight.Bold
                )
            }
        }
    }
}

