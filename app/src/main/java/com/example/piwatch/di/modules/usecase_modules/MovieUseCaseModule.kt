package com.example.piwatch.di.modules.usecase_modules

import com.example.piwatch.domain.repository.MovieRepository
import com.example.piwatch.domain.usecase.movie_usecase.AddRatingUseCase
import com.example.piwatch.domain.usecase.movie_usecase.CreateSessionUseCase
import com.example.piwatch.domain.usecase.movie_usecase.DeleteRatedMovieUseCase
import com.example.piwatch.domain.usecase.movie_usecase.FetchGenresFromRemoteUseCase
import com.example.piwatch.domain.usecase.movie_usecase.GetGenreUseCase
import com.example.piwatch.domain.usecase.movie_usecase.GetGenresUseCase
import com.example.piwatch.domain.usecase.movie_usecase.GetMovieDetailUseCase
import com.example.piwatch.domain.usecase.movie_usecase.GetMovieWithGenreUseCase
import com.example.piwatch.domain.usecase.movie_usecase.GetMoviesByGenreUseCase
import com.example.piwatch.domain.usecase.movie_usecase.GetMoviesForHomeScreenUseCase
import com.example.piwatch.domain.usecase.movie_usecase.GetSearchedMoviesUseCase
import com.example.piwatch.domain.usecase.movie_usecase.GetSimilarMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieUseCaseModule {
    @Provides
    @Singleton
    fun provideGetMoviesForHomeScreenUseCase(
        movieRepository: MovieRepository
    ): GetMoviesForHomeScreenUseCase = GetMoviesForHomeScreenUseCase(movieRepository)


    @Provides
    @Singleton
    fun provideGetMovieDetailUseCase(
        movieRepository: MovieRepository
    ): GetMovieDetailUseCase = GetMovieDetailUseCase(movieRepository)

    @Provides
    @Singleton
    fun provideGetSimilarMoviesUseCase(
        movieRepository: MovieRepository
    ): GetSimilarMoviesUseCase = GetSimilarMoviesUseCase(movieRepository)

    @Provides
    @Singleton
    fun provideGetSearchedMoviesUseCase(
        movieRepository: MovieRepository
    ): GetSearchedMoviesUseCase = GetSearchedMoviesUseCase(movieRepository)

    @Provides
    @Singleton
    fun provideGetMovieWithGenre(
        movieRepository: MovieRepository
    ): GetMoviesByGenreUseCase = GetMoviesByGenreUseCase(movieRepository)

    @Provides
    @Singleton
    fun provideGetGenres(
        movieRepository: MovieRepository
    ): GetGenresUseCase = GetGenresUseCase(movieRepository)

    @Provides
    @Singleton
    fun provideGetMovieWithGenres(
        movieRepository: MovieRepository
    ): GetMovieWithGenreUseCase = GetMovieWithGenreUseCase(movieRepository)

    @Provides
    @Singleton
    fun provideGetGenreUseCase(
        movieRepository: MovieRepository
    ): GetGenreUseCase = GetGenreUseCase(movieRepository)

    @Provides
    @Singleton
    fun provideSessionIdUserCase(
        movieRepository: MovieRepository
    ): CreateSessionUseCase = CreateSessionUseCase(movieRepository)

    @Provides
    @Singleton
    fun provideAddRatingUseCase(
        movieRepository: MovieRepository
    ): AddRatingUseCase = AddRatingUseCase(movieRepository)



    @Provides
    @Singleton
    fun provideDeleteRatedMoviesUseCase(
        movieRepository: MovieRepository
    ): DeleteRatedMovieUseCase = DeleteRatedMovieUseCase(movieRepository)

    @Provides
    @Singleton
    fun provideFetchGenresFromRemoteUseCase(
        movieRepository: MovieRepository
    ): FetchGenresFromRemoteUseCase = FetchGenresFromRemoteUseCase(movieRepository)


}