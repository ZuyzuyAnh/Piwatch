package com.example.piwatch.domain.repository

import com.example.piwatch.domain.model.Genre
import com.example.piwatch.domain.model.Movie
import com.example.piwatch.domain.model.MovieDetail
import com.example.piwatch.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    //Remote
    suspend fun getMovieList(): Flow<Resource<List<Movie>>>

    suspend fun fetchDataFromRemote()

    suspend fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>>

    suspend fun getSimilarMovies(movieId: Int): Flow<Resource<List<Movie>>>

    suspend fun getSearchedMovies(query: String, page: Int): Flow<Resource<List<Movie>>>

    suspend fun fetchGenreFromRemote()

    suspend fun getGenres(): List<Genre>

    suspend fun getMovieWithGenre(genreId: String): Flow<Resource<List<Movie>>>
}