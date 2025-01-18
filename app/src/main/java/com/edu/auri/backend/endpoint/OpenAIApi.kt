package com.edu.auri.backend.endpoint


import com.edu.auri.backend.endpoint.ChatCompletionRequest
import com.edu.auri.backend.endpoint.ChatCompletionResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAIApi {

    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    suspend fun getChatCompletion(
        @Body request: ChatCompletionRequest
    ): ChatCompletionResponse
}


