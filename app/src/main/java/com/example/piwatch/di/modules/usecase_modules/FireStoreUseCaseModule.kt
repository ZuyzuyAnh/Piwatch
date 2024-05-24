package com.example.piwatch.di.modules.usecase_modules

import com.example.piwatch.domain.repository.FireStoreService
import com.example.piwatch.domain.usecase.firestore_usecase.AddMovieToHistoryUseCase
import com.example.piwatch.domain.usecase.firestore_usecase.AddMovieToPlaylistUseCase
import com.example.piwatch.domain.usecase.firestore_usecase.AddNewPlaylistUseCase
import com.example.piwatch.domain.usecase.firestore_usecase.AddUserPlayListsUseCase
import com.example.piwatch.domain.usecase.firestore_usecase.DeletePlaylistUseCase
import com.example.piwatch.domain.usecase.firestore_usecase.GetPlaylistUseCase
import com.example.piwatch.domain.usecase.firestore_usecase.GetSessionIdUsecase
import com.example.piwatch.domain.usecase.firestore_usecase.GetUserHistoryUseCase
import com.example.piwatch.domain.usecase.firestore_usecase.LoadUserPlaylistUseCase
import com.example.piwatch.domain.usecase.firestore_usecase.RemoveMovieFromPlaylistUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FireStoreUseCaseModule {
    @Provides
    @Singleton
    fun provideGetSessionIdUseCase(
        fireStoreService: FireStoreService
    ): GetSessionIdUsecase = GetSessionIdUsecase(fireStoreService)


    @Provides
    @Singleton
    fun provideGetPlaylistUseCase(
        fireStoreService: FireStoreService
    ): GetPlaylistUseCase = GetPlaylistUseCase(fireStoreService)


    @Provides
    @Singleton
    fun provideAddUserPlaylistUseCase(
        fireStoreService: FireStoreService
    ): AddUserPlayListsUseCase = AddUserPlayListsUseCase(fireStoreService)

    @Provides
    @Singleton
    fun provideLoadingUserPlaylistUseCase(
        fireStoreService: FireStoreService
    ): LoadUserPlaylistUseCase = LoadUserPlaylistUseCase(fireStoreService)

    @Provides
    @Singleton
    fun provideAddMovieToHistoryUseCase(
        fireStoreService: FireStoreService
    ): AddMovieToHistoryUseCase = AddMovieToHistoryUseCase(fireStoreService)

    @Provides
    @Singleton
    fun provideAddNewPlaylistUseCase(
        fireStoreService: FireStoreService
    ): AddNewPlaylistUseCase = AddNewPlaylistUseCase(fireStoreService)

    @Provides
    @Singleton
    fun provideDeletePlaylistUseCase(
        fireStoreService: FireStoreService
    ): DeletePlaylistUseCase = DeletePlaylistUseCase(fireStoreService)

    @Provides
    @Singleton
    fun provideGetUserHistoryUseCase(
        fireStoreService: FireStoreService
    ): GetUserHistoryUseCase = GetUserHistoryUseCase(fireStoreService)

    @Provides
    @Singleton
    fun provideRemoveMovieFromPlaylistUseCase(
        fireStoreService: FireStoreService
    ): RemoveMovieFromPlaylistUseCase = RemoveMovieFromPlaylistUseCase(fireStoreService)

    @Provides
    @Singleton
    fun provideAddMovieToPlaylistUseCase(
        fireStoreService: FireStoreService
    ): AddMovieToPlaylistUseCase = AddMovieToPlaylistUseCase(fireStoreService)
}