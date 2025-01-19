//package com.edu.auri.backend.endpoint
//
//class `(depreciated) OpenAIRepository` {
//    private val openAIApi = ApiClient.openAIApi
//
//    suspend fun getLLMResponse(userInput: String, apiKey: String): String {
//        val request = ChatCompletionRequest(
//            model = "gpt-3.5-turbo",
//            messages = listOf(
//                Message(role = "user", content = userInput)
//            ),
//            max_tokens = 100,
//            temperature = 0.5
//        )
//
//        // Make the network request here
//        val response = openAIApi.createChatCompletion("Bearer $apiKey", request).execute()
//        if (response.isSuccessful) {
//            return response.body()?.choices?.firstOrNull()?.message?.content ?: ""
//        } else {
//            // handle error
//            throw Exception(response.errorBody()?.string())
//        }
//    }
//}