package com.example.piwatch.di.modules.usecase_modules

import com.example.piwatch.domain.repository.AuthRepository
import com.example.piwatch.domain.repository.FireStoreService
import com.example.piwatch.domain.repository.MovieRepository
import com.example.piwatch.domain.usecase.login_usecase.GetUserUsecase
import com.example.piwatch.domain.usecase.login_usecase.GoogleSignInUseCase
import com.example.piwatch.domain.usecase.login_usecase.LoginUsecase
import com.example.piwatch.domain.usecase.login_usecase.PassWordResetUseCase
import com.example.piwatch.domain.usecase.login_usecase.SignupUsecase
import com.example.piwatch.domain.usecase.form_validate.ValidateEmail
import com.example.piwatch.domain.usecase.form_validate.ValidatePassword
import com.example.piwatch.domain.usecase.form_validate.ValidatePasswordConfirm
import com.example.piwatch.domain.usecase.form_validate.ValidateUsername
import com.example.piwatch.domain.usecase.movie_usecase.GetMovieDetailUseCase
import com.example.piwatch.domain.usecase.movie_usecase.GetMoviesByGenreUseCase
import com.example.piwatch.domain.usecase.movie_usecase.GetMoviesForHomeScreenUseCase
import com.example.piwatch.domain.usecase.movie_usecase.GetSearchedMoviesUseCase
import com.example.piwatch.domain.usecase.movie_usecase.GetSimilarMoviesUseCase
import com.example.piwatch.domain.usecase.user_playlist_usecase.AddUserPlayListsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideGetUserUseCase(
        authRepository: AuthRepository
    ): GetUserUsecase = GetUserUsecase(authRepository)

    @Provides
    @Singleton
    fun provideLoginUseCase(
        authRepository: AuthRepository
    ): LoginUsecase = LoginUsecase(authRepository)

    @Provides
    @Singleton
    fun provideSignupUseCase(
        authRepository: AuthRepository
    ): SignupUsecase = SignupUsecase(authRepository)

    @Provides
    @Singleton
    fun provideValidateEmail(): ValidateEmail = ValidateEmail()

    @Provides
    @Singleton
    fun provideValidateUsername(): ValidateUsername = ValidateUsername()

    @Provides
    @Singleton
    fun provideValidatePassword(): ValidatePassword = ValidatePassword()

    @Provides
    @Singleton
    fun provideValidatePasswordConfirm(): ValidatePasswordConfirm = ValidatePasswordConfirm()

    @Provides
    @Singleton
    fun provideGoogleSignInUsecase(
        authRepository: AuthRepository
    ): GoogleSignInUseCase = GoogleSignInUseCase(authRepository)


    @Provides
    @Singleton
    fun provideSendPasswordResetUseCase(
        authRepository: AuthRepository
    ): PassWordResetUseCase = PassWordResetUseCase(authRepository)

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
    fun provideAddUserPlaylistUseCase(
        fireStoreService: FireStoreService
    ): AddUserPlayListsUseCase = AddUserPlayListsUseCase(fireStoreService)
}