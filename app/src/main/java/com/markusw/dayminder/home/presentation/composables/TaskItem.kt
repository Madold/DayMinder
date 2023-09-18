@file:OptIn(ExperimentalLayoutApi::class, ExperimentalLayoutApi::class,
    ExperimentalLayoutApi::class
)

package com.markusw.dayminder.home.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markusw.dayminder.R
import com.markusw.dayminder.core.domain.model.Task
import com.markusw.dayminder.core.utils.TimeUtils
import com.markusw.dayminder.ui.theme.DayMinderTheme

@Composable
fun TaskItem(
    task: Task,
    onClick: () -> Unit,
    onToggleClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val backgroundColor =
        if (task.importance == Task.IMPORTANCE_HIGH) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primaryContainer
    val toggleBackgroundColor =
        if (task.importance == Task.IMPORTANCE_HIGH) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onPrimaryContainer

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .clickable(onClick = onClick)
            .background(backgroundColor)
            .padding(all = 12.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f)
        ) {
            FlowRow(
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
            onClick = onToggleClick,
            backgroundColor = toggleBackgroundColor,
            iconTint = backgroundColor,
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
        "${TimeUtils.formatHourFromTimestamp(timestamp)} ${
            TimeUtils.formatDateFromTimestamp(
                timestamp
            )
        }"
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
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    iconTint: Color = MaterialTheme.colorScheme.onPrimaryContainer,
) {
    IconButton(
        modifier = modifier,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = backgroundColor,
        ),
        onClick = onClick
    ) {
        AnimatedVisibility(
            visible = isDone,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
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
                title = "This is a long tittle for my task by the way, it can be larger",
                description = "Description 1",
                timestamp = TimeUtils.getDeviceHourInTimestamp(),
                isDone = false,
                isScheduled = true,
                importance = Task.IMPORTANCE_NORMAL
            ),
            onClick = {},
            onToggleClick = {}
        )
    }
}
