package com.edu.auri

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.edu.auri.backend.notifications.NotificationReceiver
import com.edu.auri.backend.registration.AuthViewModel
import com.edu.auri.frontend.navigation.AuriNavigation
import com.edu.auri.ui.theme.AuriTheme
import java.util.Calendar

/**
 * Main activity of the Auri application.
 *
 * This activity schedules a daily notification, applies an edge-to-edge UI mode, and sets the
 * content view using Jetpack Compose. It provides the navigation graph through [AuriNavigation]
 * and manages authentication state via [AuthViewModel].
 */
class MainActivity : ComponentActivity() {

    /**
     * Called when the activity is starting.
     *
     * This method performs the following actions:
     * - Schedules a daily notification using [scheduleDailyNotification].
     * - Enables edge-to-edge content display.
     * - Initializes an instance of [AuthViewModel] via [viewModels].
     * - Sets the Compose content with [AuriTheme] and a [Scaffold] that hosts the navigation graph.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleDailyNotification()
        enableEdgeToEdge()
        val authViewModel: AuthViewModel by viewModels()
        setContent {
            AuriTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AuriNavigation(
                        modifier = Modifier.padding(innerPadding),
                        authViewModel = authViewModel
                    )
                }
            }
        }
    }

    /**
     * Schedules a daily notification using AlarmManager.
     *
     * This method creates a repeating alarm that fires a broadcast triggering
     * [NotificationReceiver]. The alarm is scheduled to start 2 minutes from the current time
     * (for testing purposes) and repeats daily. The scheduled time is logged for verification.
     */
    private fun scheduleDailyNotification() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create an Intent to trigger NotificationReceiver with an optional test action.
        val intent = Intent(this, NotificationReceiver::class.java).apply {
            action = "com.edu.auri.TEST_NOTIFICATION"
        }

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Calculate the time 2 minutes from now for testing.
        val currentTime = System.currentTimeMillis()
        val calendar = Calendar.getInstance().apply {
            timeInMillis = currentTime
            add(Calendar.MINUTE, 2) // Notification will be triggered 2 minutes from now
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // Log the scheduled time for verification.
        Log.d("MainActivity", "Scheduling notification at: ${calendar.time}")

        // Ensure the scheduled time is in the future.
        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        // Schedule a repeating alarm to trigger daily.
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}