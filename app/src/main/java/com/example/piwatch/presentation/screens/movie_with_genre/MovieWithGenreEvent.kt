package com.example.piwatch.presentation.screens.movie_with_genre

sealed class MovieWithGenreEvent {
    object nextPage : MovieWithGenreEvent()
    object previousPage : MovieWithGenreEvent()
}