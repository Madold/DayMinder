package com.markusw.dayminder.core.domain

data class ValidationResult(
    val success: Boolean,
    val errorMessage: String? = null,
)
