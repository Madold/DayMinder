package com.markusw.dayminder.core.domain.use_cases

import com.markusw.dayminder.core.domain.ValidationResult
import javax.inject.Inject

class ValidateTaskTitle @Inject constructor() {

    operator fun invoke(title: String): ValidationResult {

        if (title.isBlank()) {
            return ValidationResult(
                success = false,
                errorMessage = "Title canno't be blank."
            )
        }

        return ValidationResult(success = true)
    }

}