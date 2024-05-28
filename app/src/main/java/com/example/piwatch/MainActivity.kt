package com.example.piwatch

import AppNavHost
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.piwatch.presentation.screens.welcome_screen.SplashViewModel
import com.example.piwatch.ui.theme.PiWatchTheme
import com.example.piwatch.worker.FetchMovieFromRemoteWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel


    private lateinit var worker: WorkManager
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        worker = WorkManager.getInstance(this)
        lifecycleScope.launch {
            splashViewModel.isFirstTime.collect { isFirstTime ->
                if (isFirstTime == true) {
                    Log.d("is first time", isFirstTime.toString())
                    val constraints = Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()

                    val periodicWorkRequest = PeriodicWorkRequest.Builder(
                        FetchMovieFromRemoteWorker::class.java,
                        24, TimeUnit.HOURS
                    )
                        .setConstraints(constraints)
                        .build()
                    worker.enqueue(periodicWorkRequest)
                }
            }
        }
        installSplashScreen().setKeepOnScreenCondition {
            !splashViewModel.isLoading.value
        }
        setContent {
            PiWatchTheme {
                val screen by splashViewModel.startDestination
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface,
                ) {
                    Surface(
                        tonalElevation = 2.dp,
                        color = MaterialTheme.colorScheme.surface,
                    ) {
                        if (screen.isNotEmpty()) {
                            AppNavHost(
                                navController,
                                screen
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PiWatchTheme {
    }
}