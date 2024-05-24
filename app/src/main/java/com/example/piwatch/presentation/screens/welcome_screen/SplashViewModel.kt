package com.example.piwatch.presentation.screens.welcome_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piwatch.data.DataStoreRepository
import com.example.piwatch.domain.usecase.auth_usecase.GetUserUsecase
import com.example.piwatch.domain.usecase.movie_usecase.FetchGenresFromRemoteUseCase
import com.example.piwatch.presentation.navigation.AppRoute
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val getUserUsecase: GetUserUsecase,
    private val fetchGenresFromRemoteUseCase: FetchGenresFromRemoteUseCase
) : ViewModel() {
    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf("")
    val startDestination: State<String> = _startDestination

    private val _isFirstTime: MutableState<Boolean> = mutableStateOf(false)
    val isFirstTime: State<Boolean> = _isFirstTime

    init {
        viewModelScope.launch {
            dataStoreRepository.readOnBoardingState().collect { completed ->
                _isFirstTime.value = completed
                if (completed) {
                    var user: FirebaseUser? = null
                    viewModelScope.async(IO) {
                        user = getUserUsecase.execute()
                    }.await()
                    Log.d("inspect splash user", "${user?.uid}")
                    if (user == null) {
                        _startDestination.value = AppRoute.LOGIN.route
                    } else {
                        _startDestination.value = AppRoute.HOME.route
                    }
                } else {
                    _startDestination.value = AppRoute.WELCOME.route
                    viewModelScope.launch(IO) {
                        fetchGenresFromRemoteUseCase.execute()
                    }
                }
            }
            _isLoading.value = false
        }
    }
}