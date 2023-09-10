package com.markusw.dayminder.core.domain

import com.markusw.dayminder.core.presentation.UiText

data class ValidationResult(
    val success: Boolean,
    val errorMessage: UiText? = null,
)
