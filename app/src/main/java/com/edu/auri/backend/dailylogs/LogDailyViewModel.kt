package com.edu.auri.backend.dailylogs

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale

class LogDailyViewModel : ViewModel() {

    private val database = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    private val _dailyLogs = MutableStateFlow<Map<String, DailyLog>>(emptyMap())
    val dailyLogs = _dailyLogs.asStateFlow()

    private val _singleDayLog = MutableStateFlow<DailyLog?>(null)
    val singleDayLog = _singleDayLog.asStateFlow()


    /**
     * Saves a daily log to Firestore under "dailyLogs" subcollection.
     */
    fun saveDailyLog(dailyLog: DailyLog, date: String) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.e("SaveDailyLog", "User not authenticated")
            return
        }

        database.collection("users")
            .document(currentUser.uid)
            .collection("dailyLogs")
            .document(date)
            .set(dailyLog)
            .addOnSuccessListener {
                Log.d("SaveDailyLog", "Daily log saved successfully for date: $date")
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

    /**
     * Fetch all daily logs from Firestore (for calendar view).
     */
    fun fetchDailyLogs() {
        val currentUser = auth.currentUser ?: return

        viewModelScope.launch {
            try {
                val snapshot = database.collection("users")
                    .document(currentUser.uid)
                    .collection("dailyLogs")
                    .get()
                    .await()

                val logs = snapshot.documents.mapNotNull { doc ->
                    val log = doc.toObject(DailyLog::class.java)
                    log?.timestamp?.toDate()?.let { date ->
                        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
                        formattedDate to log
                    }
                }.toMap()

                _dailyLogs.value = logs
                Log.d("FetchDailyLogs", "Retrieved ${logs.size} logs")
            } catch (e: Exception) {
                Log.e("FetchDailyLogs", "Error fetching logs: ${e.message}", e)
            }
        }
    }

    /**
     * Fetch a single daily log by date (for specific features).
     */
    fun getDailyLogByDate(date: String) {
        val currentUser = auth.currentUser ?: return

        viewModelScope.launch {
            try {
                val document = database.collection("users")
                    .document(currentUser.uid)
                    .collection("dailyLogs")
                    .document(date)
                    .get()
                    .await()

                if (document.exists()) {
                    _singleDayLog.value = document.toObject(DailyLog::class.java)
                } else {
                    _singleDayLog.value = null
                }
            } catch (e: Exception) {
                Log.e("GetDailyLog", "Error fetching daily log: ${e.message}", e)
            }
        }
    }
}