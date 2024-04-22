package com.example.piwatch.presentation.screens.signup_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piwatch.R
import com.example.piwatch.domain.usecase.firestore_usecase.AddUserPlayListsUseCase
import com.example.piwatch.domain.usecase.form_validate.ValidateEmail
import com.example.piwatch.domain.usecase.form_validate.ValidatePassword
import com.example.piwatch.domain.usecase.form_validate.ValidatePasswordConfirm
import com.example.piwatch.domain.usecase.form_validate.ValidateUsername
import com.example.piwatch.domain.usecase.login_usecase.SignupUsecase
import com.example.piwatch.domain.usecase.movie_usecase.CreateSessionUseCase
import com.example.piwatch.util.Resource
import com.google.firebase.auth.FirebaseAuth
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
    private val firebaseAuth: FirebaseAuth,
    private val createSessionUseCase: CreateSessionUseCase
): ViewModel() {


    //Trạng thái của trang đăng kí
    private val _state = MutableStateFlow(SignUpFormState())
    val state = _state.asStateFlow()

    private var userId: String? = null
    private var sessionId: String? = null

    init {
        viewModelScope.launch {
            createSession()
        }
    }
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
                        passwordConfrimError = passwordConfrimResult.errorMessage,
                        errorToast = null
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
                    }
                    viewModelScope.launch(IO) {
                        addUserPlayListsUseCase.execute(userId!!, sessionId!!)
                    }
                    _state.value = _state.value.copy(
                        isSignUpSucess = true,
                        isLoading = false,
                        toast = R.string.signup_success
                    )
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        errorToast = result.message!!,
                        isLoading = false
                    )
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