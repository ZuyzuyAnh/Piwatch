package com.example.piwatch.domain.usecase.movie_usecase

import com.example.piwatch.domain.repository.MovieRepository
import javax.inject.Inject

class AddRatingUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun execute(movieId: Int, sessionId: String, rating: Float) = movieRepository.addRating(
        movieId, rating, sessionId
    )
}