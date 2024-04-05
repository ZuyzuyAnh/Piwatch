package com.example.piwatch.presentation.screens.library_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piwatch.data.repositoryImpl.AuthEvent
import com.example.piwatch.domain.repository.MovieRepository
import com.example.piwatch.domain.usecase.login_usecase.GetUserUsecase
import com.example.piwatch.domain.usecase.movie_usecase.GetMoviesByGenreUseCase
import com.example.piwatch.domain.usecase.user_playlist_usecase.AddUserPlayListsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getUserUsecase: GetUserUsecase,
    private val movieRepository: MovieRepository,
): ViewModel() {
    private lateinit var userId: String

    private val _libraryState = MutableStateFlow(LibraryState())
    val libraryState = _libraryState.asStateFlow()

    var isDialogShown by mutableStateOf(false)
        private set

    var playListName by mutableStateOf("")
        private set

    init {
        Log.d("library viewModel", "${_libraryState.value}")
        viewModelScope.launch(IO) {
            delay(2000)
            getGenres()
        }
    }

    fun onBuyClick(){
        isDialogShown = true
    }

    fun onDismissDialog(){
        isDialogShown = false
    }

    fun onValueChange(name: String){
        playListName = name
    }
    suspend fun getGenres(){
        _libraryState.value = _libraryState.value.copy(
            genres = movieRepository.getGenres()
        )
    }

    suspend fun getUserId(){
        try {
            getUserUsecase.execute().collect{
                if(it is AuthEvent.LogedIn){
                    userId = it.user!!.uid
                }else{
                    Log.d("No User found", "")
                }
            }
        }catch (e: Exception){

        }
    }
}