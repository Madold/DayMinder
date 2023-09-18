package com.markusw.dayminder.home.presentation.composables

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.markusw.dayminder.R
import com.markusw.dayminder.core.presentation.composables.AppButton
import com.markusw.dayminder.core.presentation.composables.AppDialog
import com.markusw.dayminder.core.presentation.composables.OutlinedAppButton

@Composable
fun DeleteTaskDialog(
    title: @Composable () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {

    AppDialog(
        modifier = modifier,
        title = title,
        buttons = {
            OutlinedAppButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.cancel))
            }
            Spacer(modifier = Modifier.width(8.dp))
            AppButton(onClick = onConfirm) {
                Text(text = stringResource(id = R.string.delete))
            }
        },
        onDismissRequest = onDismissRequest,
        content = content
    )

}