package com.edu.auri.backend.endpoint


data class ChatCompletionResponse(
    val choices: List<Choice>
) {
    data class Choice(
        val message: Message
    )
}
