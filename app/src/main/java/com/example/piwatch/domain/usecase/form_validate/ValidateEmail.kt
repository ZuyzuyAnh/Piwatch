package com.example.piwatch.domain.usecase.form_validate

import android.util.Patterns
import com.example.piwatch.util.ValidateResult
import com.example.piwatch.R

class ValidateEmail {

    fun execute(email: String): ValidateResult{
        if(email.isBlank()){
            return ValidateResult(
                success = false,
                errorMessage = R.string.blank_email
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidateResult(
                success = false,
                errorMessage = R.string.wrong_email
            )
        }
        return ValidateResult(
            success = true
        )
    }
}