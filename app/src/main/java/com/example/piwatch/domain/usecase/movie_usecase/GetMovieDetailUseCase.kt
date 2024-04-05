package com.example.piwatch.domain.usecase.movie_usecase

import com.example.piwatch.domain.model.MovieDetail
import com.example.piwatch.domain.repository.MovieRepository
import com.example.piwatch.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetMovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository
){
    suspend fun execute(movieId: Int): Flow<Resource<MovieDetail>> = movieRepository.getMovieDetail(movieId)
}