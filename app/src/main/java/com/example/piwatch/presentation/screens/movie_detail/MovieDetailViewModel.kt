package com.example.piwatch.presentation.screens.movie_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piwatch.domain.model.Movie
import com.example.piwatch.domain.model.MovieDetail
import com.example.piwatch.domain.repository.FireStoreService
import com.example.piwatch.domain.usecase.firestore_usecase.GetSessionIdUsecase
import com.example.piwatch.domain.usecase.login_usecase.GetUserUsecase
import com.example.piwatch.domain.usecase.movie_usecase.AddRatingUseCase
import com.example.piwatch.domain.usecase.movie_usecase.GetMovieDetailUseCase
import com.example.piwatch.domain.usecase.movie_usecase.GetSimilarMoviesUseCase
import com.example.piwatch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val fireStoreService: FireStoreService,
    private val getUserUsecase: GetUserUsecase,
    private val addRatingUseCase: AddRatingUseCase,
    private val getSessionIdUsecase: GetSessionIdUsecase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    lateinit var userId: String
    private val _movieDetailState = MutableStateFlow(MovieDetailState())
    val movieDetailState = _movieDetailState.asStateFlow()

    private val movieId: Int = savedStateHandle["movie_id"]!!

    private var sessionId: String? = null
    var isDialogShown by mutableStateOf(false)
        private set

    var isRatingSuccess: Boolean = false

    var isRatingShown by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch(IO) {
            getMovieDetailState()
        }

        viewModelScope.launch {
            delay(500)
            viewModelScope.launch(IO) {
                loadUserPlayList()
            }
            viewModelScope.launch(IO) {
                getSessionId()
            }

        }
        viewModelScope.launch {
            getUserId()
        }
    }

    fun onBuyClick() {
        isDialogShown = true
    }

    fun onDismissDialog() {
        isDialogShown = false
    }

    fun onRatingClick() {
        isRatingShown = true
    }

    fun onDismissRating() {
        isRatingShown = false
    }

    suspend fun getMovieDetailState() {
        val movieDetail = getMovieDetailUseCase.execute(movieId)
        val similarMovies = getSimilarMoviesUseCase.execute(movieId)
        movieDetail.zip(similarMovies) { first, second ->
            if (first is Resource.Success && second is Resource.Success) {
                return@zip Resource.Success(
                    first.result?.let {
                        val newMovie = MovieDetail(
                            backdropPath = it.backdropPath,
                            genres = it.genres,
                            id = it.id.toInt(),
                            imdbId = it.imdbId,
                            overview = it.overview,
                            posterPath = it.posterPath,
                            releaseDate = it.releaseDate,
                            status = it.status,
                            title = it.title,
                            voteAverage = it.voteAverage
                        )
                        MovieDetailState(
                            movie = newMovie,
                            similarMovies = second.result!!,
                            trailerKey = it.trailerKey
                        )
                    }
                )
            } else {
                return@zip Resource.Loading()
            }
        }.collect { result ->
            when (result) {
                is Resource.Success -> {
                    result.result!!.let { it ->
                        _movieDetailState.value = _movieDetailState.value.copy(
                            movie = it.movie,
                            similarMovies = it.similarMovies,
                            trailerKey = it.trailerKey
                        )
                    }
                }

                is Resource.Loading -> {
                    _movieDetailState.value = _movieDetailState.value.copy(isLoading = true)
                }

                is Resource.Error -> {
                    _movieDetailState.value = _movieDetailState.value.copy(isLoading = false)
                }
            }
        }
    }

    suspend fun getUserId() {
        userId = getUserUsecase.execute()!!.uid
    }

    suspend fun loadUserPlayList() {
        try {
            fireStoreService.getUserPlayLists(userId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _movieDetailState.value = _movieDetailState.value.copy(
                            playList = result.result?.playLists!!,
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {

                    }

                    is Resource.Error -> {
                    }
                }
            }
        } catch (e: Exception) {
        }
    }

    suspend fun addMovieToPlaylist(playListId: Int) {
        viewModelScope.launch {
            fireStoreService.addMoviesToPlayList(
                userId = userId,
                movie = stateToMovie(),
                playListId = playListId
            ).collect { result ->
                if (result is Resource.Success) {
                    _movieDetailState.value = _movieDetailState.value.copy(
                        isAddingLoading = true,
                        shouldShowToast = true,
                        message = result.result!!
                    )
                    delay(500)
                    loadUserPlayList()
                    _movieDetailState.value = _movieDetailState.value.copy(isAddingLoading = false)
                } else {
                    _movieDetailState.value = _movieDetailState.value.copy(
                        message = result.result!!
                    )
                }
            }
        }
    }

    suspend fun removeMovieFromPlaylist(playListId: Int) {
        viewModelScope.launch {
            fireStoreService.removeMovieFromPlaylist(
                userId = userId,
                movie = stateToMovie(),
                playListId = playListId
            ).collect { result ->
                if (result is Resource.Success) {
                    _movieDetailState.value = _movieDetailState.value.copy(
                        isAddingLoading = true,
                        shouldShowToast = true,
                        message = result.result!!
                    )
                    delay(400)
                    loadUserPlayList()
                    _movieDetailState.value = _movieDetailState.value.copy(isAddingLoading = false)
                } else {
                    _movieDetailState.value = _movieDetailState.value.copy(
                        isAddingLoading = true,
                        shouldShowToast = true,
                        message = result.result!!
                    )
                }
            }
        }
    }

    fun addMovieToHistory() {
        viewModelScope.launch {
            fireStoreService.addMoviesToHistory(
                userId,
                movie = stateToMovie()
            )
        }
    }

    private fun stateToMovie(): Movie {
        return Movie(
            id = _movieDetailState.value.movie.id.toLong(),
            posterPath = _movieDetailState.value.movie.posterPath,
            title = _movieDetailState.value.movie.title,
            voteAverage = _movieDetailState.value.movie.voteAverage,
        )
    }

    fun addRating(value: Float) {
        viewModelScope.launch(IO) {
            addRatingUseCase.execute(_movieDetailState.value.movie.id, sessionId!!, value)
                .collect { result ->
                    if (result is Resource.Success) {
                        _movieDetailState.value = _movieDetailState.value.copy(
                            message = result.result!!,
                            isRatingSuccess = true,
                            shouldShowToast = true
                        )
                        delay(1000)
                        _movieDetailState.value = _movieDetailState.value.copy(
                            isRatingSuccess = false,
                        )
                    } else {
                        _movieDetailState.value = _movieDetailState.value.copy(
                            message = result.result!!,
                            isRatingSuccess = false,
                            shouldShowToast = true
                        )
                        delay(1000)
                        _movieDetailState.value = _movieDetailState.value.copy(
                            isRatingSuccess = true,
                        )
                    }
                }
        }
    }

    suspend fun getSessionId() {
        getSessionIdUsecase.execute(userId).collect { result ->
            if (result is Resource.Success) {
                sessionId = result.result.toString()
            }
        }
    }

    fun turnOffToast() {
        _movieDetailState.value = _movieDetailState.value.copy(
            shouldShowToast = false
        )
    }
}