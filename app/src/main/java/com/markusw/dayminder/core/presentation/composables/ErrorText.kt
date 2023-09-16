package com.markusw.dayminder.core.presentation.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun ErrorText(
    modifier: Modifier = Modifier,
    text: String? = null,
    style: TextStyle = TextStyle()
) {
    text?.let {
        Text(
            modifier = modifier,
            text = it,
            style = style,
            color = MaterialTheme.colorScheme.error
        )
    }
}