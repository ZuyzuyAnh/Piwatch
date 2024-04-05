package com.example.piwatch.presentation.screens.movie_detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piwatch.domain.model.Movie
import com.example.piwatch.domain.usecase.movie_usecase.GetMovieDetailUseCase
import com.example.piwatch.domain.usecase.movie_usecase.GetSimilarMoviesUseCase
import com.example.piwatch.presentation.screens.home_screen.HomeState
import com.example.piwatch.util.Resource
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _movieDetailState = MutableStateFlow(MovieDetailState())
    val movieDetailState = _movieDetailState.asStateFlow()

    private val movieId: Int = savedStateHandle["movie_id"]!!

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getMovieDetailState()
            Log.d("movieDetail", "${_movieDetailState.value}")
        }

    }

    suspend fun getMovieDetailState(){
        val movieDetail = getMovieDetailUseCase.execute(movieId)
        val similarMovies = getSimilarMoviesUseCase.execute(movieId)
        movieDetail.zip(similarMovies){first, second ->
            Log.d("inspect", "${second.result}")
            if(first is Resource.Success && second is Resource.Success){
                return@zip Resource.Success(
                    first.result?.let {
                        MovieDetailState(
                            backdropPath = it.backdropPath,
                            genres = it.genres.map { it.name },
                            id = it.id,
                            imdbId = it.imdbId,
                            overview = it.overview,
                            posterPath = it.posterPath,
                            releaseDate = it.releaseDate,
                            status = it.status,
                            title = it.title,
                            voteAverage = it.voteAverage,
                            similarMovies = second.result!!,
                            isLoading = false,
                            trailerKey = it.trailerKey
                        )
                    }
                )
            }else if(first is Error || second is Error){
                return@zip Resource.Error(
                    message = "Something wrong",
                    data = null
                )
            }else{
                return@zip Resource.Loading()
            }
        }.collect{result ->
            when(result){
                is Resource.Success -> {
                    _movieDetailState.value = result.result!!
                }
                is Resource.Loading -> {
                    _movieDetailState.value = _movieDetailState.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _movieDetailState.value = _movieDetailState.value.copy(errorMessage = result.message, isLoading = true)
                }
            }
        }
    }


}