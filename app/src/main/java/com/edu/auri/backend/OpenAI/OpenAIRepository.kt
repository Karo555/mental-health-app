package com.edu.auri.backend.OpenAI

import android.util.Log

/**
 * Repository class responsible for interacting with the OpenAI Chat API.
 *
 * This class contains methods to build and send requests to the OpenAI API and retrieve chat completions.
 */
class OpenAIRepository {

    /**
     * Fetches a chat completion from the OpenAI API using the provided prompt.
     *
     * The method constructs a [ChatRequest] based on the input [prompt] and sends it to the API
     * using the Retrofit service provided by [OpenAIClient]. If the API call is successful, it extracts
     * and returns the generated reply; otherwise, it returns `null`.
     *
     * @param prompt the user-provided input to generate the chat completion.
     * @return the generated chat response as a [String], or `null` if an error occurs or the API response is unsuccessful.
     */
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