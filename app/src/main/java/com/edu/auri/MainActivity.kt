package com.edu.auri

import com.edu.auri.frontend.history.HistoryScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.edu.auri.backend.dailylogs.LogDailyViewModel
import com.edu.auri.backend.registration.AuthViewModel
import com.edu.auri.frontend.navigation.AuriNavigation
import com.edu.auri.ui.theme.AuriTheme

/**
 * Main activity of the Auri application.
 *
 * This activity is responsible for setting up the application's UI using Jetpack Compose.
 * It initializes necessary ViewModels and manages the navigation system.
 */
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is starting. Sets up the UI with the app theme and navigation.
     *
     * @param savedInstanceState If the activity is re-initialized after being shut down,
     * this Bundle contains the data it most recently supplied.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel: AuthViewModel by viewModels()
        val logDailyViewModel: LogDailyViewModel by viewModels()

        setContent {
            AuriTheme {
                val navController = rememberNavController()
                Scaffold { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        AuriNavigation(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            authViewModel = authViewModel,
                            logDailyViewModel = logDailyViewModel
                        )
                        HistoryScreen(navController = navController, viewModel = logDailyViewModel)
                    }
                }
            }
        }
    }
}