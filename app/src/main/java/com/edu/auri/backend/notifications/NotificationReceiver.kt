package com.edu.auri.backend.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.edu.auri.R
import com.edu.auri.backend.dailylogs.LogDailyActivity

/**
 * Broadcast receiver that handles triggering notifications for mood tracking reminders.
 *
 * When an appropriate broadcast is received, this receiver creates and displays a notification,
 * which, when tapped, launches the [LogDailyActivity] for logging daily mood data.
 */
class NotificationReceiver : BroadcastReceiver() {

    companion object {
        /**
         * The unique identifier for the notification channel.
         */
        const val CHANNEL_ID = "mood_tracking_channel"

        /**
         * The identifier for the notification.
         */
        const val NOTIFICATION_ID = 1
    }

    /**
     * Called when the BroadcastReceiver receives an Intent broadcast.
     *
     * This method builds and displays a notification for a mood tracking reminder. The notification,
     * when tapped, launches the [LogDailyActivity]. Additionally, a notification channel is created
     * for Android 8.0 (Oreo) and above.
     *
     * @param context The Context in which the receiver is running.
     * @param intent The received broadcast Intent (this parameter may be `null`).
     */
    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("NotificationReceiver", "onReceive called: triggering notification")

        // Create an intent to launch LogDailyActivity when the notification is tapped.
        val activityIntent = Intent(context, LogDailyActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Build the notification.
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)  // Ensure that you have an appropriate drawable resource.
            .setContentTitle("Mood Tracker Reminder")
            .setContentText("Don't forget to log your mood today!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Create the notification channel (required for Android 8.0+).
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Mood Tracking Reminders",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Channel for daily mood tracking reminders"
        }
        notificationManager.createNotificationChannel(channel)

        // Show the notification.
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}