package com.markusw.dayminder.home.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dayminder.R
import com.markusw.dayminder.core.domain.model.Task
import com.markusw.dayminder.core.utils.TimeUtils
import com.markusw.dayminder.home.presentation.HomeUiEvent
import com.markusw.dayminder.ui.theme.DayMinderTheme

@Composable
fun TaskItem(
    task: Task,
    onEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(if (task.importance == Task.IMPORTANCE_HIGH) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primaryContainer)
            .padding(all = 12.dp)
            .clickable { },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                TaskTitle(
                    title = task.title,
                    isCrossedOut = task.isDone
                )
                if (task.importance == Task.IMPORTANCE_HIGH) {
                    ImportanceIndicator()
                }
            }
            if (task.isScheduled) {
                TaskScheduledTime(timestamp = task.timestamp)
            }
        }

        TaskToggle(
            isDone = task.isDone,
            onEvent = onEvent,
            backgroundColor = if (task.importance == Task.IMPORTANCE_HIGH) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onPrimaryContainer,
            iconTint = if (task.importance == Task.IMPORTANCE_HIGH) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primaryContainer
        )

    }

}

@Composable
private fun TaskTitle(
    title: String,
    isCrossedOut: Boolean = false
) {
    Text(
        text = title,
        style = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            textDecoration = if (isCrossedOut) TextDecoration.LineThrough else TextDecoration.None
        )
    )
}

@Composable
private fun ImportanceIndicator() {
    Box(
        modifier = Modifier
            .size(12.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.error)

    )
}

@Composable
private fun TaskScheduledTime(
    timestamp: Long
) {

    val formattedTime = remember {
        "${TimeUtils.formatHourFromTimestamp(timestamp)} ${TimeUtils.formatDateFromTimestamp(timestamp)}"
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(id = R.drawable.ic_clock),
            contentDescription = null
        )
        Text(
            text = formattedTime,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
private fun TaskToggle(
    isDone: Boolean,
    onEvent: (HomeUiEvent) -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    iconTint: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    IconButton(
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = backgroundColor,
        ),
        onClick = { }
    ) {
        AnimatedVisibility(visible = isDone, enter = fadeIn(), exit = fadeOut()) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = null,
                tint = iconTint
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskItemPreview() {
   DayMinderTheme(
         darkTheme = false,
         dynamicColor = false
   ) {
       TaskItem(
           task = Task(
               id = 1,
               title = "Task 1",
               description = "Description 1",
               timestamp = TimeUtils.getDeviceHourInTimestamp(),
               isDone = true,
               isScheduled = true,
               importance = Task.IMPORTANCE_HIGH
           ),
           onEvent = {

           }
       )
   }
}
