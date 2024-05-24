package com.example.piwatch.di.modules.usecase_modules

import com.example.piwatch.domain.repository.AuthRepository
import com.example.piwatch.domain.usecase.form_validate.ValidateEmail
import com.example.piwatch.domain.usecase.form_validate.ValidatePassword
import com.example.piwatch.domain.usecase.form_validate.ValidatePasswordConfirm
import com.example.piwatch.domain.usecase.form_validate.ValidateUsername
import com.example.piwatch.domain.usecase.auth_usecase.GetUserUsecase
import com.example.piwatch.domain.usecase.auth_usecase.GoogleSignInUseCase
import com.example.piwatch.domain.usecase.auth_usecase.LoginUsecase
import com.example.piwatch.domain.usecase.auth_usecase.PassWordResetUseCase
import com.example.piwatch.domain.usecase.auth_usecase.SignOutUseCase
import com.example.piwatch.domain.usecase.auth_usecase.SignupUsecase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthenticationUseCaseModule {
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
    fun provideSignOutUseCase(
        firebaseAuth: FirebaseAuth
    ): SignOutUseCase = SignOutUseCase(firebaseAuth)
}