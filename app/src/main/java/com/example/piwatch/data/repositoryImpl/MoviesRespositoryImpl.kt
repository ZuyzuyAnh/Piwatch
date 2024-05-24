package com.example.piwatch.data.repositoryImpl

import android.util.Log
import com.example.piwatch.R
import com.example.piwatch.data.local.MovieDatabase
import com.example.piwatch.data.local.model.toGenre
import com.example.piwatch.data.local.model.toMovie
import com.example.piwatch.data.remote.TMDBService
import com.example.piwatch.data.remote.model.Rating
import com.example.piwatch.data.remote.model.genres.toGenreEntity
import com.example.piwatch.data.remote.model.movieDetail.toMovieDetail
import com.example.piwatch.data.remote.model.movieList.toMovies
import com.example.piwatch.domain.model.Genre
import com.example.piwatch.domain.model.Movie
import com.example.piwatch.domain.model.MovieDetail
import com.example.piwatch.domain.model.toMovieEntity
import com.example.piwatch.domain.repository.MovieRepository
import com.example.piwatch.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRespositoryImpl @Inject constructor(
    private val tmdbService: TMDBService,
    private val movieDb: MovieDatabase
): MovieRepository {

    private val movieDao = movieDb.movieDao()
    private val genreDao = movieDb.genreDao()

    override suspend fun getMovieList(
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            val localMovies = withContext(IO) {
                movieDao.getAllMovies()
            }
            Log.d("localMovie", "$localMovies")
            emit(Resource.Success(
                data = localMovies.map { it.toMovie() }
            ))
        }
    }

    override suspend fun fetchDataFromRemote() {
        val remoteMovies = try {
            val popularDeferred = CoroutineScope(Dispatchers.IO).async {
                tmdbService.getPopularMovies().body()?.toMovies(isPopular = true) ?: emptyList()
            }
            val topRatedDeferred = CoroutineScope(Dispatchers.IO).async {
                tmdbService.getTopRatedMovies().body()?.toMovies(isTopRated = true) ?: emptyList()
            }
            val upComingDeferred = CoroutineScope(Dispatchers.IO).async {
                tmdbService.getUpcomingMovies().body()?.toMovies(isUpcoming = true) ?: emptyList()
            }

            val popular = popularDeferred.await()
            val topRated = topRatedDeferred.await()
            val upComing = upComingDeferred.await()

            listOf(popular, topRated, upComing).flatten()
        }catch (e: Exception){
            e.printStackTrace()
            Log.d("remoteError", "{${e.message}}")
            null
        }
        remoteMovies?.let {list ->
            withContext(IO){
                movieDao.deleteAllMovies()
                movieDao.saveMovies(
                    list.map { it.toMovieEntity() }
                )
            }
        }
    }

    override suspend fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> {
        return flow {
            try {
                emit(Resource.Loading())
                val movieDetail = tmdbService.getMovieDetails(movieId).body()?.toMovieDetail()
                val videos = tmdbService.getMovieVideos(movieId).body()
                movieDetail?.trailerKey = videos?.results?.get(0)?.key
                Log.d("movieDetailRepository", "$movieDetail")
                emit(Resource.Success(movieDetail))
            }catch (e: Exception){
                emit(Resource.Error(e.message!!))
            }
        }
    }

    override suspend fun getSimilarMovies(movieId: Int): Flow<Resource<List<Movie>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val similarMovies = tmdbService.getSimilarMovies(movieId).body()?.toMovies()
                emit(Resource.Success(similarMovies))
            }catch (e: Exception){
                emit(Resource.Error(e.message!!))
            }
        }
    }

    override suspend fun getSearchedMovies(query: String, page: Int): Flow<Resource<List<Movie>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val similarMovies = tmdbService.getSearchedMovie(query,page).body()?.toMovies()
                emit(Resource.Success(similarMovies))
            }catch (e: Exception){
                emit(Resource.Error(e.message!!))
            }
        }
    }

    override suspend fun fetchGenreFromRemote() {
        val remoteGenre = try {
            tmdbService.getGenres().body()?.genres?: emptyList()
        }catch (e: Exception){
            e.printStackTrace()
            emptyList()
        }
        withContext(IO){
            genreDao.insertGenre(remoteGenre.map { it.toGenreEntity() })
        }
    }

    override suspend fun getGenres(): Flow<Resource<List<Genre>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val genres = genreDao.getAllGenre()
                emit(Resource.Success(genres.map { it.toGenre() }))
            } catch (e: Exception) {
                emit(Resource.Error(e.message!!))
            }
        }
    }

    override suspend fun getGenre(genreId: Int): Flow<Resource<String>> {
        return flow {
            try {
                emit(Resource.Loading())
                val genreName = genreDao.getGenre(genreId)
                emit(Resource.Success(genreName))
            } catch (e: Exception) {
                emit(Resource.Error(e.message!!))
            }
        }
    }

    override suspend fun getMovieWithGenre(genreId: Int, page: Int): Flow<Resource<List<Movie>>> {
        return flow {
            try{
                emit(Resource.Loading())
                val movies = tmdbService.getMovieByGenre(page, genreId).body()?.toMovies()
                emit(Resource.Success(movies))
            }catch (e: Exception){
                emit(Resource.Error(e.message!!))
            }
        }
    }

    override suspend fun addRating(
        movieId: Int,
        data: Float,
        sessionId: String
    ): Flow<Resource<Int>> {
        return flow {
            try {
                val rating = Rating(data.toString())
                tmdbService.addRating(movieId, sessionId, rating)
                emit(Resource.Success(R.string.rate_success))
            } catch (e: Exception) {
                emit(Resource.Error(e.message!!))
            }
        }
    }

    override suspend fun createGuestSession(): Flow<Resource<String>> {
        return flow {
            try {
                val sessionId = tmdbService.createGuestSession().body()?.guestSessionId
                Log.d("create session id repo", "$sessionId")
                emit(
                    Resource.Success(
                        sessionId
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error(e.message!!))
            }
        }
    }

    override suspend fun deleteRatedMovie(sessionId: String, movieId: Int) {
        tmdbService.deleteRatedMovie(movieId, sessionId)
    }


}