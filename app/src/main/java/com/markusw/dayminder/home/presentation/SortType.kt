package com.markusw.dayminder.home.presentation

import com.example.dayminder.R
import com.markusw.dayminder.core.presentation.UiText


sealed class SortType(
    val text: UiText
) {

    companion object {
        fun values() = listOf(
            MyDay,
            Important
        )
    }

    data object MyDay: SortType(
        text = UiText.StringResource(R.string.my_day)
    )
    data object Important: SortType(
        text = UiText.StringResource(R.string.important)
    )
}