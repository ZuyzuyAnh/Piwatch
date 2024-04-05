package com.example.piwatch.domain.usecase.movie_usecase

import com.example.piwatch.domain.repository.MovieRepository
import java.util.concurrent.Flow
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun execute(
        genreId: String
    ) = movieRepository.getMovieWithGenre(genreId)
}