package com.edu.auri.backend.OpenAI

import android.util.Log
import com.edu.auri.backend.dailylogs.DailyLogDataClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * A repository class for handling data operations with Firestore.
 *
 * This class provides methods to fetch user data and daily logs from Firestore.
 *
 * @property firestore an instance of [FirebaseFirestore]. By default, it uses [FirebaseFirestore.getInstance].
 */
class DataRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    /**
     * Fetches data from the specified top-level collection in Firestore.
     *
     * The method retrieves all documents from the collection named [collectionName] and returns
     * a list of maps representing document data. If an error occurs during the fetch operation,
     * an empty list is returned.
     *
     * @param collectionName the name of the Firestore collection from which to fetch data.
     * @return a [List] of [Map] instances, where each map contains key-value pairs representing
     *         the document data, or an empty list if an error occurs.
     */
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

    /**
     * Fetches a specific daily log for the given user and date.
     *
     * This method navigates to the "daily_logs" subcollection of the specified user and retrieves
     * the document corresponding to the provided [date]. If the document exists, it converts the
     * data into a [DailyLogDataClass] object. If the document does not exist or an error occurs,
     * the method returns `null`.
     *
     * @param userId the identifier of the user whose daily log is being fetched.
     * @param date the date string representing the specific day for which to retrieve the log.
     * @return an instance of [DailyLogDataClass] if the document exists and conversion is successful;
     *         otherwise, `null`.
     */
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

    /**
     * Fetches all daily logs for the specified user.
     *
     * The method retrieves all documents from the "daily_logs" subcollection for the given [userId]
     * and converts them into a list of [DailyLogDataClass] objects. If an error occurs during the
     * fetch operation, an empty list is returned.
     *
     * @param userId the identifier of the user whose daily logs are to be fetched.
     * @return a [List] of [DailyLogDataClass] instances representing the daily logs,
     *         or an empty list if an error occurs.
     */
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