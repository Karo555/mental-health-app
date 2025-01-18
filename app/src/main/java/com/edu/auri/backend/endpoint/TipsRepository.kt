package com.edu.auri.backend.endpoint
import android.util.Log
import com.edu.auri.FakeOpenAIApi
import com.google.firebase.firestore.FirebaseFirestore


class TipsRepository(
    private val firestoreService: FirestoreService,
    private val openAIApi: OpenAIApi
) {
    companion object {
        private const val TAG = "TipsRepository"

        @Volatile
        private var instance: TipsRepository? = null

        fun getInstance(firestore: FirebaseFirestore, openAIApi: FakeOpenAIApi): TipsRepository {
            val firestoreService = FirestoreService(firestore)
            return instance ?: synchronized(this) {
                instance ?: TipsRepository(firestoreService, openAIApi).also { instance = it }
            }
        }
    }

    suspend fun fetchDailyLogs(userId: String, date: String): Result<List<DailyLog>> {
        return try {
            val path = "users/$userId/daily_logs/$date/data"
            val result = firestoreService.getCollection(path)
            result.map { documents ->
                documents.mapNotNull { document ->
                    document.toDailyLog()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching daily logs: ${e.message}", e)
            Result.failure(e)
        }
    }

    private fun Map<String, Any>.toDailyLog(): DailyLog? {
        return try {
            DailyLog(
                alcohol = (this["alcohol"] as? Long)?.toInt() ?: 0,
                angerLevel = (this["angerLevel"] as? Long)?.toInt() ?: 0,
                anxietyLevel = (this["anxietyLevel"] as? Long)?.toInt() ?: 0,
                cigarettes = (this["cigarettes"] as? Long)?.toInt() ?: 0,
                cupsOfCoffee = (this["cupsOfCoffee"] as? Long)?.toInt() ?: 0,
                drugs = this["drugs"] as? Boolean ?: false,
                gratification = (this["gratification"] as? Long)?.toInt() ?: 0,
                litersOfWater = (this["litersOfWater"] as? Long)?.toInt() ?: 0,
                mood = this["mood"] as? String ?: "",
                sleepHours = (this["sleepHours"] as? Long)?.toInt() ?: 0,
                socialInteractions = (this["socialInteractions"] as? Long)?.toInt() ?: 0,
                stressLevel = (this["stressLevel"] as? Long)?.toInt() ?: 0,
                sweets = (this["sweets"] as? Long)?.toInt() ?: 0,
                timestamp = this["timestamp"] as? String ?: "",
                workoutTime = (this["workoutTime"] as? Long)?.toInt() ?: 0
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing document to DailyLog: ${e.message}", e)
            null
        }
    }

    suspend fun analyzeDataWithOpenAI(logs: List<DailyLog>): Result<String> {
        return try {
            // Build messages for the OpenAI prompt
            val messages = logs.map { log ->
                Message(role = "user", content = "Daily Log:\nMood: ${log.mood}\nStress Level: ${log.stressLevel}")
            }

            val request = ChatCompletionRequest(
                model = "gpt-4",
                messages = messages,
                max_tokens = 150,
                temperature = 0.7
            )

            val response = openAIApi.getChatCompletion(request)
            val generatedText = response.choices.firstOrNull()?.message?.content
                ?: "No suggestions available."

            Result.success(generatedText)
        } catch (e: Exception) {
            Log.e("TipsRepository", "OpenAI API error: ${e.message}", e)
            Result.failure(e)
        }
    }

}
