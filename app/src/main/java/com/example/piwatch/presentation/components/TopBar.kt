package com.example.piwatch.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun CustomTopBar(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "",
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = modifier
                .wrapContentSize()
                .clickable { navigateBack() }
        ) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
        }
        HeadingTextComponent(text = "$title", weight = FontWeight.ExtraBold)
        Spacer(modifier = modifier)
    }
}