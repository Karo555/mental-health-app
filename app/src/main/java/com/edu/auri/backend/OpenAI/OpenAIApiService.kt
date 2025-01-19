package com.edu.auri.backend.OpenAI

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// Data class representing a single chat message
data class ChatMessage(
    @SerializedName("role")
    val role: String,    // e.g., "system", "user", or "assistant"
    @SerializedName("content")
    val content: String
)

// Data class for the request body to the Chat Completions endpoint
data class ChatRequest(
    @SerializedName("model")
    val model: String,       // For example, "gpt-3.5-turbo" or "gpt-4"
    @SerializedName("messages")
    val messages: List<ChatMessage>,
    @SerializedName("max_tokens")
    val maxTokens: Int = 150,
    @SerializedName("temperature")
    val temperature: Double = 0.7
)

// Data class representing each choice in the response
data class ChatChoice(
    @SerializedName("message")
    val message: ChatMessage
)

// Data class for the response from the Chat Completions endpoint
data class ChatResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("choices")
    val choices: List<ChatChoice>
)

// Retrofit interface for the OpenAI Chat API
interface OpenAIApiService {

    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    suspend fun getChatCompletions(@Body request: ChatRequest): Response<ChatResponse>

    companion object {
        const val BASE_URL = "https://api.openai.com/"
    }
}