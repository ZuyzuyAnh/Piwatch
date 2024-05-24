package com.example.piwatch.presentation.screens.login_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piwatch.domain.usecase.form_validate.ValidateEmail
import com.example.piwatch.domain.usecase.form_validate.ValidatePassword
import com.example.piwatch.domain.usecase.auth_usecase.GoogleSignInUseCase
import com.example.piwatch.domain.usecase.auth_usecase.LoginUsecase
import com.example.piwatch.domain.usecase.firestore_usecase.AddUserPlayListsUseCase
import com.example.piwatch.domain.usecase.movie_usecase.CreateSessionUseCase
import com.example.piwatch.util.Resource
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val addUserPlayListsUseCase: AddUserPlayListsUseCase,
    private val createSessionUseCase: CreateSessionUseCase
): ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private var sessionId: String? = null

    init {
        viewModelScope.launch {
            createSession()
        }
    }
    fun onEvent(event: LoginEvent){
        when(event){
            is LoginEvent.EmailChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is LoginEvent.PasswordChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }
            is LoginEvent.Submit -> {
                val emailResult = validateEmail.execute(_state.value.email)
                val passwordResult = validatePassword.execute(_state.value.password)

                val hasError = listOf(
                    emailResult,
                    passwordResult,
                ).any{ !it.success }

                if(hasError){
                    _state.value = _state.value.copy(
                        emailError = emailResult.errorMessage,
                        passwordError = passwordResult.errorMessage,
                    )
                    return

                }else{
                    _state.value = _state.value.copy(
                        emailError = null,
                        passwordError = null,
                    )
                    Log.d("noError", "")
                    viewModelScope.launch {
                        login(
                            _state.value.email,
                            _state.value.password,
                        )
                    }
                }
            }
            is LoginEvent.LoginWithGoogle -> {
                viewModelScope.launch {
                    googleSignIn(event.credential)
                }
            }
        }
    }

    suspend fun login(email: String, password: String) = viewModelScope.launch {
        loginUsecase.execute(email, password).collect{result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        loginError = null,
                        isLoginSuccess = true,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(loginError = result.message, isLoading = false)
                }
            }

        }
    }

    fun googleSignIn(credential: AuthCredential) = viewModelScope.launch {
        googleSignInUseCase.execute(credential).collect{result ->
            when(result){
                is Resource.Success -> {
                    Log.d("loginState", "$result")
                    _state.value = _state.value.copy(loginError = null, isLoginSuccess = true)
                    viewModelScope.launch(Dispatchers.IO) {
                        addUserPlayListsUseCase.execute(result.result?.user?.uid!!, sessionId!!)
                    }
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    Log.d("loginState", "$result")
                    _state.value = _state.value.copy(loginError = result.message, )
                }
            }
        }
    }
    suspend fun createSession() {
        createSessionUseCase.execute().collect { result ->
            when (result) {
                is Resource.Success -> {
                    sessionId = result.result
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {

                }
            }
        }
    }
}