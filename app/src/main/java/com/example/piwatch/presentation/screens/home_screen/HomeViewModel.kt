package com.example.piwatch.presentation.screens.home_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piwatch.data.repositoryImpl.AuthEvent
import com.example.piwatch.domain.repository.MovieRepository
import com.example.piwatch.domain.usecase.login_usecase.GetUserUsecase
import com.example.piwatch.domain.usecase.movie_usecase.GetMoviesForHomeScreenUseCase
import com.example.piwatch.util.Resource
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesForHomeScreenUseCase: GetMoviesForHomeScreenUseCase,
): ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState
    init {
        viewModelScope.launch(IO) {
            delay(2000)
            getMovieList()
        }
    }

    suspend fun getMovieList(){
        getMoviesForHomeScreenUseCase.execute().collect{result ->
            when(result){
                is Resource.Success -> {
                    Log.d("Home view model init", "${result.result}")
                    result.result?.let {list ->
                        val popularMovies = list.filter { it.isPopular == 1 }
                        val topRatedMovies = list.filter{ it.isTopRated == 1}
                        val upComingMovies = list.filter { it.isUpcoming == 1 }
                        _homeState.value = _homeState.value.copy(
                            popularMovies = popularMovies,
                            topRatedMovies = topRatedMovies,
                            upcomingMovies = upComingMovies
                        )
                    }
                }
                is Resource.Loading -> {
                    _homeState.value = _homeState.value.copy(isLoading = true)
                }
                is Resource.Error -> Unit
            }
        }
    }
}