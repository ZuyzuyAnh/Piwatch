package com.example.piwatch.domain.usecase.movie_usecase

import com.example.piwatch.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun execute(
        genreId: Int,
        page: Int
    ) = movieRepository.getMovieWithGenre(genreId, page)
}