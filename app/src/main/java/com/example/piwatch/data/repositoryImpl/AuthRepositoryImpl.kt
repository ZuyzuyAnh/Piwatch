package com.example.piwatch.data.repositoryImpl

import android.provider.Settings.Global.getString
import android.util.Log
import com.example.piwatch.domain.repository.AuthRepository
import com.example.piwatch.util.Resource
import com.example.piwatch.util.await
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : AuthRepository {
    override suspend fun getCurrentUser(): Flow<AuthEvent> {
        return flow {
            try {
                val currentUser = firebaseAuth.currentUser
                if(currentUser == null){
                    emit(AuthEvent.LogedOut)
                }else{
                    emit(AuthEvent.LogedIn(
                        currentUser
                    ))
                }
            }catch (e: Exception){

            }
        }
    }


    override suspend fun loginnUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(
                email, password
            ).await()
            emit(Resource.Success(result))

        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override suspend fun signUpUser(
        userName: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(userName).build())?.await()
            emit(Resource.Success(result))
        } .catch{
            emit(Resource.Error(it.message.toString()))
        }
    }

    override suspend fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithCredential(credential).await()
            emit(Resource.Success(result))
        }.catch{
            emit(Resource.Error(it.message.toString()))
        }
    }

    override suspend fun forgotpassword(email: String): Flow<Resource<Void>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.sendPasswordResetEmail(email).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }



    override suspend fun logout() {
        firebaseAuth.signOut()
    }


}