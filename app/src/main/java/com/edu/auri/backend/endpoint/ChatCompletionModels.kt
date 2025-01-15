package com.edu.auri.backend.endpoint

data class ChatCompletionRequest(
    val model: String,
    val messages: List<Message>,
    val max_tokens: Int? = null,
    val temperature: Double? = null
)

data class Message(
    val role: String,
    val content: String
)

data class ChatCompletionResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val choices: List<Choice>,
    val usage: Usage?
)

data class Choice(
    val index: Int,
    val message: Message,
    val finish_reason: String?
)

data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)

