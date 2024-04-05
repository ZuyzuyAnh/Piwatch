package com.example.piwatch.domain.usecase.form_validate

import android.util.Patterns
import com.example.piwatch.util.ValidateResult
import com.example.piwatch.R
import java.util.regex.Pattern

class ValidateUsername {

    fun execute(userName: String): ValidateResult{
        if(userName.isBlank()){
            return ValidateResult(
                success = false,
                errorMessage = R.string.blank_username
            )
        }
        return ValidateResult(
            success = true
        )
    }
}