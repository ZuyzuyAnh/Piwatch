package com.example.piwatch.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.piwatch.data.DataStoreRepository
import com.example.piwatch.data.local.MovieDatabase
import com.example.piwatch.data.remote.TMDBService
import com.example.piwatch.data.repositoryImpl.AuthRepositoryImpl
import com.example.piwatch.data.repositoryImpl.FireStoreServiceImpl
import com.example.piwatch.data.repositoryImpl.MoviesRespositoryImpl
import com.example.piwatch.domain.repository.AuthRepository
import com.example.piwatch.domain.repository.FireStoreService
import com.example.piwatch.domain.repository.MovieRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository = impl

    @Singleton
    @Provides
    fun provideFireStore(): FirebaseFirestore = Firebase.firestore

    @Singleton
    @Provides
    fun provideFireStoreService(
        impl: FireStoreServiceImpl
    ): FireStoreService = impl

    @Singleton
    @Provides
    fun provideMovieRemoteService(): TMDBService {
        return Retrofit.Builder()
            .baseUrl(TMDBService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDBService::class.java)
    }


    @Singleton
    @Provides
    fun provideMovieDatabase(application: Application): MovieDatabase{
        return Room.databaseBuilder(
            application,
            MovieDatabase::class.java,
            "moviedb"
        ).build()
    }

    @Singleton
    @Provides
    fun provideMovieRepository(
        tmdbService: TMDBService,
        movieDb: MovieDatabase
    ): MovieRepository = MoviesRespositoryImpl(tmdbService, movieDb)

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)
}