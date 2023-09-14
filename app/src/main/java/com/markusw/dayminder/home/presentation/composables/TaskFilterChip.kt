package com.markusw.dayminder.home.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.markusw.dayminder.ui.theme.DayMinderTheme

@Composable
fun TaskFilterChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    text: @Composable () -> Unit,
) {

    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background

    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = backgroundColor,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = RoundedCornerShape(15.dp)
    ) {
        text()
    }
}

@Preview(showBackground = true)
@Composable
fun TaskFilterChipPreview() {
    DayMinderTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        TaskFilterChip(
            onClick = { /*TODO*/ },
            text = {
                Text(text = "My Day")
            },
            isSelected = false
        )
    }
}