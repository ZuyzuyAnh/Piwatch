package com.example.piwatch.domain.usecase.form_validate

import com.example.piwatch.R
import com.example.piwatch.util.ValidateResult

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