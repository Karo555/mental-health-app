package com.edu.auri.backend.OpenAI

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Data class representing a single chat message.
 *
 * @property role the role of the message sender (e.g., "system", "user", or "assistant").
 * @property content the content of the chat message.
 */
data class ChatMessage(
    @SerializedName("role")
    val role: String,    // e.g., "system", "user", or "assistant"
    @SerializedName("content")
    val content: String
)

/**
 * Data class for constructing the request body for the Chat Completions endpoint.
 *
 * @property model the model to use for generating completions (e.g., "gpt-3.5-turbo" or "gpt-4").
 * @property messages a list of [ChatMessage] objects representing the conversation history.
 * @property maxTokens the maximum number of tokens to generate in the response (default is 150).
 * @property temperature the sampling temperature to control randomness in the response (default is 0.7).
 */
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

/**
 * Data class representing a single choice in the chat completions response.
 *
 * @property message the [ChatMessage] containing the generated message details.
 */
data class ChatChoice(
    @SerializedName("message")
    val message: ChatMessage
)

/**
 * Data class representing the response from the Chat Completions endpoint.
 *
 * @property id the unique identifier for the response.
 * @property choices a list of [ChatChoice] objects containing the generated chat choices.
 */
data class ChatResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("choices")
    val choices: List<ChatChoice>
)

/**
 * Retrofit interface for interacting with the OpenAI Chat API.
 *
 * This interface defines the API endpoint and configuration for obtaining chat completions.
 */
interface OpenAIApiService {

    /**
     * Sends a POST request to the Chat Completions endpoint to generate chat responses.
     *
     * The request body should contain the required parameters encapsulated in a [ChatRequest] object.
     *
     * @param request the [ChatRequest] containing the request parameters.
     * @return a [Response] object containing the [ChatResponse] returned from the API.
     */
    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    suspend fun getChatCompletions(@Body request: ChatRequest): Response<ChatResponse>

    companion object {
        /**
         * The base URL for accessing the OpenAI API.
         *
         * This should point to the root endpoint of the OpenAI API.
         */
        const val BASE_URL = "https://api.openai.com/"
    }
}