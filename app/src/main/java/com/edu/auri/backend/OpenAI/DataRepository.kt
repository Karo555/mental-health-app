package com.edu.auri.backend.OpenAI

import android.util.Log
import com.edu.auri.backend.dailylogs.DailyLogDataClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DataRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    // Fetches data from a top-level collection.
    suspend fun fetchUserData(collectionName: String): List<Map<String, Any>> {
        Log.d("FirestoreDebug", "Inside fetchUserData(), about to query Firestore...")
        return try {
            val snapshot = firestore.collection(collectionName).get().await()
            Log.d("FirestoreDebug", "Snapshot obtained. Document count: ${snapshot.documents.size}")
            snapshot.documents.mapNotNull { it.data }
        } catch (e: Exception) {
            Log.e("DataRepository", "Error fetching data: ${e.message}", e)
            emptyList()
        }
    }

    // Fetches a specific daily log for a given user and date.
    suspend fun fetchDailyLog(userId: String, date: String): DailyLogDataClass? {
        return try {
            // Navigate to the specific document in the "daily_logs" subcollection
            val docSnapshot = firestore.collection("users")
                .document(userId)
                .collection("daily_logs")
                .document(date)
                .get()
                .await()

            Log.d("FirestoreDebug", "Raw document data: ${docSnapshot.data}")

            if (docSnapshot.exists()) {
                Log.d("FirestoreDebug", "Daily log found for user: $userId on date: $date")
                // Convert the snapshot into your DailyLogDataClass
                docSnapshot.toObject(DailyLogDataClass::class.java)
            } else {
                Log.d("FirestoreDebug", "No daily log found for user: $userId on date: $date")
                null
            }
        } catch (e: Exception) {
            Log.e("DataRepository", "Error fetching daily log: ${e.message}", e)
            null
        }
    }

    // Fetches all daily logs for a user.
    suspend fun fetchAllDailyLogs(userId: String): List<DailyLogDataClass> {
        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("daily_logs")
                .get()
                .await()
            Log.d("FirestoreDebug", "Fetched ${snapshot.documents.size} daily logs for user: $userId")
            snapshot.documents.mapNotNull { it.toObject(DailyLogDataClass::class.java) }
        } catch (e: Exception) {
            Log.e("DataRepository", "Error fetching daily logs: ${e.message}", e)
            emptyList()
        }
    }
}
