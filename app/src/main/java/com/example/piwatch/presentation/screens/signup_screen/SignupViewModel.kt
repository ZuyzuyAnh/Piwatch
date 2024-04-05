package com.example.piwatch.presentation.screens.signup_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piwatch.data.repositoryImpl.AuthEvent
import com.example.piwatch.domain.usecase.login_usecase.SignupUsecase
import com.example.piwatch.domain.usecase.form_validate.ValidateEmail
import com.example.piwatch.domain.usecase.form_validate.ValidatePassword
import com.example.piwatch.domain.usecase.form_validate.ValidatePasswordConfirm
import com.example.piwatch.domain.usecase.form_validate.ValidateUsername
import com.example.piwatch.domain.usecase.login_usecase.GetUserUsecase
import com.example.piwatch.domain.usecase.user_playlist_usecase.AddUserPlayListsUseCase
import com.example.piwatch.presentation.screens.login_screen.LoginState
import com.example.piwatch.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUsecase: SignupUsecase,
    private val validateUsername: ValidateUsername,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validatePasswordConfirm: ValidatePasswordConfirm,
    private val addUserPlayListsUseCase: AddUserPlayListsUseCase,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {


    //Trạng thái của trang đăng kí
    private val _state = MutableStateFlow(SignUpFormState())
    val state = _state.asStateFlow()

    private var userId: String? = null

    fun onEvent(event: SignupFormEvent){
        when(event){
            is SignupFormEvent.UsernameChanged -> {
                _state.value = _state.value.copy(userName = event.userName)
            }
            is SignupFormEvent.EmailChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is SignupFormEvent.PasswordChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }
            is SignupFormEvent.PasswordConfirmChanged -> {
                _state.value = _state.value.copy(passwordConfrim = event.passwordConfirm)
            }
            is SignupFormEvent.Submit -> {

                val emailResult = validateEmail.execute(_state.value.email)
                val userNameResult = validateUsername.execute(_state.value.userName)
                val passwordResult = validatePassword.execute(_state.value.password)
                val passwordConfrimResult = validatePasswordConfirm.execute(_state.value.password,_state.value.passwordConfrim)

                val hasError = listOf(
                    emailResult,
                    userNameResult,
                    passwordResult,
                    passwordConfrimResult
                ).any{ !it.success }

                if(hasError){
                    _state.value = _state.value.copy(
                        emailError = emailResult.errorMessage,
                        userNameError = userNameResult.errorMessage,
                        passwordError = passwordResult.errorMessage,
                        passwordConfrimError = passwordConfrimResult.errorMessage
                    )
                    return

                }else{
                    viewModelScope.launch {
                        signup(
                            _state.value.userName,
                            _state.value.email,
                            _state.value.password,
                            _state.value.passwordConfrim
                        )
                    }
                }
            }
        }
    }

    private fun signup(username: String, email: String, password: String, password2: String) = viewModelScope.launch {
        signupUsecase.execute(username,email, password, password2).collect{result ->
            when(result){
                is Resource.Success -> {
                    firebaseAuth.currentUser?.uid?.let {
                        userId = it
                        // Sử dụng userId ở đây theo nhu cầu của bạn
                    }
                    _state.value = _state.value.copy(isSignUpSucess = true, isLoading = true)
                    viewModelScope.launch(IO) {
                        addUserPlayListsUseCase.execute(userId!!)
                    }
                    _state.value = _state.value.copy(isSignUpSucess = true, isLoading = false)
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {

                }
            }
        }
    }
}