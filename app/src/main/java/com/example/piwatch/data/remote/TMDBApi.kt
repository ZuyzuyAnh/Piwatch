package com.example.piwatch.data.remote

import com.example.piwatch.BuildConfig
import com.example.piwatch.data.remote.model.genres.GenresDto
import com.example.piwatch.data.remote.model.movieDetail.MovieDetailDto
import com.example.piwatch.data.remote.model.movieList.MovieListDto
import com.example.piwatch.data.remote.model.movieVideo.MovieVideoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
interface TMDBService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query(
            "api_key"
        ) apiKey: String = BuildConfig.TMDB_KEY
    ): Response<MovieListDto>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query(
            "api_key"
        ) apiKey: String = BuildConfig.TMDB_KEY
    ): Response<MovieListDto>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query(
            "api_key"
        ) apiKey: String = BuildConfig.TMDB_KEY
    ): Response<MovieListDto>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query(
            "api_key"
        ) apiKey: String = BuildConfig.TMDB_KEY
    ): Response<MovieDetailDto>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query(
            "api_key"
        ) apiKey: String = BuildConfig.TMDB_KEY
    ): Response<MovieListDto>

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    @GET("search/movie")
    suspend fun getSearchedMovie(
        @Query(
            "query"
        )query: String,
        @Query(
            "page"
        )page: Int,
        @Query(
            "api_key"
        ) apiKey: String = BuildConfig.TMDB_KEY

    ): Response<MovieListDto>

    @GET("discover/movie")
    suspend fun getMovieByGenre(
        @Query(
            "with_genres"
        ) genreId: String,
        @Query(
            "api_key"
        ) apiKey: String = BuildConfig.TMDB_KEY
    ): Response<MovieListDto>

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query(
            "api_key"
        ) apiKey: String = BuildConfig.TMDB_KEY
    ): Response<GenresDto>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query(
            "api_key"
        ) apiKey: String = BuildConfig.TMDB_KEY,
    ): Response<MovieVideoDto>
}