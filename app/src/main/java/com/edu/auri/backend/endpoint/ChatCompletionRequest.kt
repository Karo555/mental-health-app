package com.edu.auri.backend.endpoint


data class ChatCompletionRequest(
    val model: String = "gpt-4",
    val messages: List<Message>,
    val max_tokens: Int,
    val temperature: Double
)

data class Message(
    val role: String, // "user", "assistant", or "system"
    val content: String
)


