package com.example.piwatch.di.modules.usecase_modules

import com.example.piwatch.domain.repository.FireStoreService
import com.example.piwatch.domain.usecase.firestore_usecase.GetSessionIdUsecase
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
}