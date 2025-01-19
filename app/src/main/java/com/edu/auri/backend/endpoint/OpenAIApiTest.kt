//package com.edu.auri.backend.endpoint
//
//import com.edu.auri.backend.OpenAIApi
//import com.edu.auri.backend.ChatCompletionRequest
//import com.edu.auri.backend.ChatCompletionResponse
//import io.mockk.coEvery
//import io.mockk.mockk
//import kotlinx.coroutines.runBlocking
//import org.junit.Assert.assertEquals
//import org.junit.Test
//
//class OpenAIApiTest {
//
//    private val mockApi: OpenAIApi = mockk()
//
//    @Test
//    fun `test OpenAIApi returns valid response`() = runBlocking {
//        // Mock response
//        val mockResponse = ChatCompletionResponse(
//            choices = listOf(
//                ChatCompletionResponse.Choice(
//                    message = ChatCompletionResponse.Choice.Message(
//                        role = "assistant",
//                        content = "Stay hydrated and reduce stress levels."
//                    )
//                )
//            )
//        )
//
//        // Mock behavior
//        coEvery { mockApi.getChatCompletion(any()) } returns mockResponse
//
//        // Call the API
//        val request = ChatCompletionRequest(
//            model = "gpt-4",
//            messages = listOf(
//                ChatCompletionRequest.Message(role = "user", content = "Example prompt")
//            ),
//            max_tokens = 150,
//            temperature = 0.7
//        )
//        val response = mockApi.getChatCompletion(request)
//
//        // Assert
//        assertEquals("Stay hydrated and reduce stress levels.", response.choices[0].message.content)
//    }
//}
