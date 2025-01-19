package com.edu.auri.backend.OpenAI

import android.util.Log

class OpenAIRepository {

    suspend fun fetchChatCompletion(prompt: String): String? {
        return try {
            Log.d("OpenAIDebug", "Sending chat request to OpenAI with prompt: $prompt")

            // Construct the messages list; the "user" role denotes that this is the user's prompt.
            val messages = listOf(
                ChatMessage(role = "user", content = prompt)
            )

            // Build the ChatRequest for the Chat Completions endpoint.
            val request = ChatRequest(
                model = "gpt-3.5-turbo",  // Or "gpt-4" if available and desired.
                messages = messages,
                maxTokens = 150,         // You can adjust this value as needed.
                temperature = 0.7
            )

            // Make the API call using the updated Retrofit service.
            val response = OpenAIClient.apiService.getChatCompletions(request)
            if (response.isSuccessful) {
                // Extract the assistant's reply from the response.
                val text = response.body()?.choices?.firstOrNull()?.message?.content?.trim()
                Log.d("OpenAIDebug", "Received chat response: $text")
                text
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("OpenAIDebug", "Error response: $errorBody")
                null
            }
        } catch (e: Exception) {
            Log.e("OpenAIDebug", "Exception during OpenAI chat call: ${e.message}", e)
            null
        }
    }
}

