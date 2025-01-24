package com.edu.auri.backend.dailylogs

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Locale

class LogDailyViewModel : ViewModel() {

    // Firestore instance
    private val database = Firebase.firestore
    // Firebase Authentication instance
    private val auth = FirebaseAuth.getInstance()

    /**
     * Saves a daily log to the "dailyLogs" subcollection under the user's document in the database.
     *
     * The daily log is saved in the path:
     * users/{userId}/dailyLogs/{date}
     *
     * @param dailyLog The DailyLog object to be saved.
     * @param date The date string in "yyyy-MM-dd" format.
     */
    fun saveDailyLog(dailyLog: DailyLog, date: String) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.e("SaveDailyLog", "User not authenticated")
            return
        }

        // Write the daily log to the user's nested subcollection "dailyLogs" with the date as document ID
        database.collection("users")
            .document(currentUser.uid)
            .collection("dailyLogs")
            .document(date) // Use date as the document ID
            .set(dailyLog)
            .addOnSuccessListener {
                Log.d(
                    "SaveDailyLog",
                    "Daily log saved successfully for date: $date"
                )
            }
            .addOnFailureListener { exception ->
                Log.e("SaveDailyLog", "Error saving daily log: ${exception.message}", exception)
            }
    }

    /**
     * Utility function to get the current date in "yyyy-MM-dd" format.
     */
    fun getCurrentDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(System.currentTimeMillis())
    }
}