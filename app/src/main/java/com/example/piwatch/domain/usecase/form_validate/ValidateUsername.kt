package com.example.piwatch.domain.usecase.form_validate

import com.example.piwatch.R
import com.example.piwatch.util.ValidateResult

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