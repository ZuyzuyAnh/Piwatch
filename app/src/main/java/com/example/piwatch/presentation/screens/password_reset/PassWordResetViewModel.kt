package com.example.piwatch.presentation.screens.password_reset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piwatch.domain.usecase.auth_usecase.PassWordResetUseCase
import com.example.piwatch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PassWordResetViewModel @Inject constructor(
    private val sendPassWordResetUseCase: PassWordResetUseCase
): ViewModel() {


    private val _sendResetFlow = MutableStateFlow<Resource<Void>?>(null)
    val sendResetFlow = _sendResetFlow.asStateFlow()

    var email by mutableStateOf("")

    fun onEvent(event: PasswordResetEvent){
        when(event){
            is PasswordResetEvent.EmailChanged -> {
                email = event.email
            }
            else ->{
                viewModelScope.launch {
                    sendPasswordRequest(email)
                }
            }
        }
    }

    suspend fun sendPasswordRequest(email: String){
        sendPassWordResetUseCase.execute(email).collect{result ->
            when(result){
                is  Resource.Loading -> {
                    _sendResetFlow.value = Resource.Loading()
                }
                is  Resource.Success -> {
                    _sendResetFlow.value = Resource.Success(
                        data = result.result
                    )
                }
                is  Resource.Error -> {
                    _sendResetFlow.value = Resource.Error(
                        message = result.message!!
                    )
                }

                else -> {}
            }

        }
    }
}