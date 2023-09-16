package com.markusw.dayminder.core.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable () -> Unit = {},
    textStyle: TextStyle = TextStyle.Default,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = false,
    cursorBrush: Brush = SolidColor(Color.Black),
    isError: Boolean = false,
    errorText: String? = null
) {

    Column {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                maxLines = maxLines,
                singleLine = singleLine,
                cursorBrush = cursorBrush,
                textStyle = textStyle
            )
            if (value.isBlank()) {
                placeholder()
            }
        }
        ErrorText(text = errorText)
    }

}