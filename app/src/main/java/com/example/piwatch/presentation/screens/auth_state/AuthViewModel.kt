package com.example.piwatch.presentation.screens.auth_state

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piwatch.data.repositoryImpl.AuthEvent
import com.example.piwatch.domain.usecase.login_usecase.GetUserUsecase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getUserUsecase: GetUserUsecase,
): ViewModel() {
    private val _authState = MutableStateFlow<AuthEvent>(AuthEvent.LogedOut)
    val authState = _authState.asStateFlow()


    init {
        viewModelScope.launch {
            getCurrentUser()
        }
    }

    private suspend fun getCurrentUser(){
        getUserUsecase.execute().collect{result ->
            _authState.value = result
            Log.d("Auth State in viewModel", "${_authState.value}")
        }
    }

}