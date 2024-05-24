package com.example.piwatch.data.remote

import com.example.piwatch.BuildConfig
import com.example.piwatch.data.remote.model.Rating
import com.example.piwatch.data.remote.model.genres.GenresDto
import com.example.piwatch.data.remote.model.guestSession.GuestSessionDto
import com.example.piwatch.data.remote.model.movieDetail.MovieDetailDto
import com.example.piwatch.data.remote.model.movieList.MovieListDto
import com.example.piwatch.data.remote.model.movieVideo.MovieVideoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
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
            "page"
        ) page: Int,
        @Query(
            "with_genres"
        ) genreId: Int,
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

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("movie/{movie_id}/rating")
    suspend fun addRating(
        @Path("movie_id") movieId: Int,
        @Query("guest_session_id") sessionId: String,
        @Body rating: Rating,
        @Query(
            "api_key"
        ) apiKey: String = BuildConfig.TMDB_KEY,
    )

    @GET("authentication/guest_session/new")
    suspend fun createGuestSession(
        @Query(
            "api_key"
        ) apiKey: String = BuildConfig.TMDB_KEY,
    ): Response<GuestSessionDto>


    @DELETE("movie/{movie_id}/rating\n")
    suspend fun deleteRatedMovie(
        @Path("movie_id") movieId: Int,
        @Query("guest_session_id") sessionId: String,
        @Query(
            "api_key"
        ) apiKey: String = BuildConfig.TMDB_KEY,
    )
    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}