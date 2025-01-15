package com.edu.auri.frontend.tips

import com.edu.auri.backend.endpoint.ChatCompletionRequest
import com.edu.auri.backend.endpoint.Message
import com.edu.auri.backend.endpoint.OpenAIApi
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TipsRepository(
    private val firestore: FirebaseFirestore,
    private val openAIApi: OpenAIApi // or your custom LLM service
) {

    suspend fun fetchTipsFromFirebase(): List<String> {
        return suspendCoroutine { continuation ->
            firestore.collection("tips")
                .get()
                .addOnSuccessListener { snapshot ->
                    val tips = snapshot.documents.mapNotNull { doc ->
                        doc.getString("alcohol")
                        doc.getString("angerLevel")
                        doc.getString("anxietyLevel")
                        doc.getString("cigarettes")
                        doc.getString("cupsOfCoffee")
                        doc.getString("drugs")
                        doc.getString("gratification")
                        doc.getString("litersOfWater")
                        doc.getString("mood")
                        doc.getString("sleepHours")
                        doc.getString("socialInteractions")
                        doc.getString("stressLevel")
                        doc.getString("sweets")
                        doc.getString("workoutTime")
                    // example field
                    }
                    continuation.resume(tips)
                }
                .addOnFailureListener { e ->
                    continuation.resumeWithException(e)
                }
        }
    }

    // Or you can define a separate approach for the LLM call
    suspend fun generateLLMResponse(userInput: String, apiKey: String): String {
        // Example using a synchronous call with .execute() if you're not using coroutines properly:
        // Or define a suspend function in your interface if you prefer.

        // Build the request object for the LLM
        val request = ChatCompletionRequest(
            model = "gpt-3.5-turbo",
            messages = listOf(Message(role = "user", content = userInput))
        )

        return withContext(Dispatchers.IO) {
            val response = openAIApi
                .createChatCompletion("Bearer $apiKey", request)
                .execute()

            if (response.isSuccessful) {
                val body = response.body()
                body?.choices?.firstOrNull()?.message?.content ?: "No content"
            } else {
                throw Exception("LLM request failed: ${response.errorBody()?.string()}")
            }
        }
    }
}
