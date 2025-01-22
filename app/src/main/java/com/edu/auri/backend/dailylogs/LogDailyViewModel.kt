package com.edu.auri.backend.dailylogs

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LogDailyViewModel : ViewModel() {

    // Firestore instance
    private val database = Firebase.firestore
    // Firebase Authentication instance
    private val auth = FirebaseAuth.getInstance()

    /**
     * Saves a daily log to the "dailyLogs" subcollection under the user's document in the database.
     *
     * The daily log is saved in the path:
     * users/{userId}/dailyLogs
     *
     * @param dailyLog The DailyLog object to be saved.
     */
    fun saveDailyLog(dailyLog: DailyLog) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.e("SaveDailyLog", "User not authenticated")
            return
        }

        // Write the daily log to the user's nested subcollection "dailyLogs"
        database.collection("users")
            .document(currentUser.uid)
            .collection("dailyLogs")
            .add(dailyLog)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    "SaveDailyLog",
                    "Daily log saved successfully with ID: ${documentReference.id}"
                )
            }
            .addOnFailureListener { exception ->
                Log.e("SaveDailyLog", "Error saving daily log: ${exception.message}", exception)
            }
    }
}

