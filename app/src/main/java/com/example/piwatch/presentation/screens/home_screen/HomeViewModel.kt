package com.example.piwatch.presentation.screens.home_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piwatch.domain.usecase.movie_usecase.GetMoviesForHomeScreenUseCase
import com.example.piwatch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
            _homeState.value = _homeState.value.copy(isLoading = true)
            delay(1000)
            getMovieList()
            _homeState.value = _homeState.value.copy(isLoading = false)
        }
    }

    suspend fun getMovieList(){
        getMoviesForHomeScreenUseCase.execute().collect{result ->
            when(result){
                is Resource.Success -> {
                    Log.d("Home view model init", "${result.result}")
                    result.result?.let {list ->
                        val popularMovies = list.filter { it.isPopular == true }
                        val topRatedMovies = list.filter { it.isTopRated == true }
                        val upComingMovies = list.filter { it.isUpcoming == true }
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