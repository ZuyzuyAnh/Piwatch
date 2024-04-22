package com.example.piwatch.presentation.screens.movie_with_genre

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piwatch.domain.usecase.movie_usecase.GetGenreUseCase
import com.example.piwatch.domain.usecase.movie_usecase.GetMovieWithGenreUseCase
import com.example.piwatch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieWithGenreViewModel @Inject constructor(
    private val getMovieWithGenreUseCase: GetMovieWithGenreUseCase,
    private val getGenreUseCase: GetGenreUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _movieWithGenreState = MutableStateFlow(MovieWithGenreState())
    val movieWithGenreState = _movieWithGenreState.asStateFlow()

    private val genreId: Int = savedStateHandle["genre_id"]!!


    var page: Int = 1

    init {
        viewModelScope.launch(IO) {
            viewModelScope.launch(IO) {
                getMovieWithGenre()
            }
            viewModelScope.launch(IO) {
                getGenre()
            }
        }
    }

    suspend fun onEvent(event: MovieWithGenreEvent) {
        when (event) {
            is MovieWithGenreEvent.nextPage -> {
                page++;
                getMovieWithGenre(page)
            }

            is MovieWithGenreEvent.previousPage -> {
                page--;
                getMovieWithGenre(page)
            }
        }
    }

    suspend fun getMovieWithGenre(page: Int = 1) {
        getMovieWithGenreUseCase.execute(genreId, page).collect { result ->
            when (result) {
                is Resource.Success -> {
                    if (page != 1) {
                        _movieWithGenreState.value = _movieWithGenreState.value.copy(
                            movies = result.result!!,
                            isLoading = false
                        )
                    } else {
                        _movieWithGenreState.value = _movieWithGenreState.value.copy(
                            movies = result.result!!,
                        )
                    }
                }

                is Resource.Loading -> {
                    _movieWithGenreState.value = _movieWithGenreState.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _movieWithGenreState.value = _movieWithGenreState.value.copy(
                        error = result.message
                    )
                }
            }
        }
    }

    suspend fun getGenre() {
        getGenreUseCase.execute(genreId).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _movieWithGenreState.value = _movieWithGenreState.value.copy(
                        isLoading = false,
                        genre = result.result
                    )
                }

                is Resource.Loading -> {
                    _movieWithGenreState.value = _movieWithGenreState.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _movieWithGenreState.value = _movieWithGenreState.value.copy(
                        error = result.message
                    )
                }
            }
        }
    }
}