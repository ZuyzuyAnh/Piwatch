package com.example.piwatch.domain.usecase.form_validate

import android.util.Patterns
import com.example.piwatch.util.ValidateResult
import com.example.piwatch.R
import java.util.regex.Pattern

class ValidatePasswordConfirm {

    fun execute(password: String, password2: String): ValidateResult{
        if(password2.isBlank()){
            return ValidateResult(
                success = false,
                errorMessage = R.string.blank_confirm_password
            )
        }
        if(password != password2){
            return ValidateResult(
                success = false,
                errorMessage = R.string.wrong_password_confirm
            )
        }
        return ValidateResult(
            success = true
        )
    }
}