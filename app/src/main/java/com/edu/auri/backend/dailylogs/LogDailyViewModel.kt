package com.edu.auri.backend.dailylogs

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class LogDailyViewModel : ViewModel() {

    // Firestore instance
    private val database = Firebase.firestore

    /**
     * Saves a daily log to the "dailyLogs" collection in the database.
     *
     * @param dailyLog The DailyLog object to be saved.
     */
    fun saveDailyLog(dailyLog: DailyLog) {
        database.collection("dailyLogs")
            .add(dailyLog)
            .addOnSuccessListener { documentReference ->
                // Log the success with the document ID
                Log.d(
                    "SaveDailyLog",
                    "Daily log saved successfully with ID: ${documentReference.id}"
                )
            }
            .addOnFailureListener { exception ->
                // Log the error for debugging purposes
                Log.e("SaveDailyLog", "Error saving daily log: ${exception.message}", exception)
            }
    }
}

