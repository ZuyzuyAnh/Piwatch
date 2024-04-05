package com.example.piwatch.domain.usecase.movie_usecase

import com.example.piwatch.domain.model.Movie
import com.example.piwatch.domain.repository.MovieRepository
import com.example.piwatch.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesForHomeScreenUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun execute(
    ): Flow<Resource<List<Movie>>> = movieRepository.getMovieList()
}