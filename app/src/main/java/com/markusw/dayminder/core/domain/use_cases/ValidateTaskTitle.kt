package com.markusw.dayminder.core.domain.use_cases

import com.example.dayminder.R
import com.markusw.dayminder.core.domain.ValidationResult
import com.markusw.dayminder.core.presentation.UiText
import javax.inject.Inject

class ValidateTaskTitle @Inject constructor() {

    operator fun invoke(title: String): ValidationResult {

        if (title.isBlank()) {
            return ValidationResult(
                success = false,
                errorMessage = UiText.StringResource(R.string.title_blank_error)
            )
        }

        return ValidationResult(success = true)
    }

}