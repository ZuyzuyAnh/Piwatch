package com.example.piwatch.domain.repository

import com.example.piwatch.domain.model.Genre
import com.example.piwatch.domain.model.Movie
import com.example.piwatch.domain.model.MovieDetail
import com.example.piwatch.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieList(): Flow<Resource<List<Movie>>>
    suspend fun fetchDataFromRemote()
    suspend fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>>
    suspend fun getSimilarMovies(movieId: Int): Flow<Resource<List<Movie>>>
    suspend fun getSearchedMovies(query: String, page: Int): Flow<Resource<List<Movie>>>
    suspend fun fetchGenreFromRemote()
    suspend fun getGenres(): Flow<Resource<List<Genre>>>
    suspend fun getGenre(genreId: Int): Flow<Resource<String>>
    suspend fun getMovieWithGenre(genreId: Int, page: Int): Flow<Resource<List<Movie>>>
    suspend fun addRating(movieId: Int, data: Float, sessionId: String): Flow<Resource<Int>>
    suspend fun createGuestSession(): Flow<Resource<String>>
    suspend fun deleteRatedMovie(sessionId: String, movieId: Int)
}