package com.example.piwatch.domain.usecase.form_validate

import android.util.Patterns
import com.example.piwatch.util.ValidateResult
import com.example.piwatch.R
import java.util.regex.Pattern

class ValidatePassword {

    fun execute(password: String): ValidateResult{
        if(password.isBlank()){
            return ValidateResult(
                success = false,
                errorMessage = R.string.blank_password
            )
        }
        else if(password.length < 8){
            return ValidateResult(
                success = false,
                errorMessage = R.string.short_password
            )
        }
        if(
            ! (password.any{it.isDigit()} && password.any { it.isLetter() })
        ){
            return ValidateResult(
                success = false,
                errorMessage = R.string.weak_password
            )
        }
        return ValidateResult(
            success = true
        )
    }
}