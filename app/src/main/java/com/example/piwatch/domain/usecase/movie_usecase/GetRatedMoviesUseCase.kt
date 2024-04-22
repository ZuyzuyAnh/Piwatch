package com.example.piwatch.domain.usecase.movie_usecase

import com.example.piwatch.domain.repository.MovieRepository
import javax.inject.Inject

class GetRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun execute(sessionId: String) = movieRepository.getRatedMovies(sessionId)
}