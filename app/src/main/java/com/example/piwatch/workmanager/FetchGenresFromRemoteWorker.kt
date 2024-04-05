package com.example.piwatch.workmanager

import android.annotation.SuppressLint
import android.content.Context
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.piwatch.domain.repository.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class FetchGenresFromRemoteWorker @AssistedInject constructor(
    private val movieRepository: MovieRepository,
    @Assisted context: Context,
    @Assisted params: WorkerParameters
): CoroutineWorker(context, params) {
    @SuppressLint("RestrictedApi")
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO){
            try {
                movieRepository.fetchGenreFromRemote()
                Result.success()
            }catch (e: HttpException){
                Result.Retry()
            }
            catch (e: Exception) {
                Result.failure()
            }
        }
    }
}