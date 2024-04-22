package com.example.piwatch.worker

import android.annotation.SuppressLint
import android.content.Context
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.piwatch.R
import com.example.piwatch.domain.repository.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

@HiltWorker
class FetchMovieFromRemoteWorker @AssistedInject constructor(
    private val movieRepository: MovieRepository,
    @Assisted context: Context,
    @Assisted params: WorkerParameters
): CoroutineWorker(context, params) {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi")
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun doWork(): Result {
        return withContext(IO){
            try {
                movieRepository.fetchDataFromRemote()
                makeNotification(
                    applicationContext.resources.getString(R.string.notification_content),
                    applicationContext
                )
                Log.d("worker", "Success")
                Result.success()
            }catch (e: HttpException){
                Log.d("worker", "${e.message}")
                Result.Retry()
            }
            catch (e: Exception) {
                Log.d("worker", "${e.message}")
                Result.failure()
            }
        }
    }

}

