package com.edu.auri

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.edu.auri.backend.endpoint.ChatCompletionRequest
import com.edu.auri.backend.endpoint.ChatCompletionResponse
import com.edu.auri.backend.endpoint.OpenAIApi
import com.edu.auri.backend.endpoint.TipsRepository
import com.edu.auri.backend.registration.AuthViewModel
import com.edu.auri.frontend.navigation.AuriNavigation
import com.edu.auri.ui.theme.AuriTheme
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Test TipsRepository
        val firestore = FirebaseFirestore.getInstance()
        val repository = TipsRepository.getInstance(firestore, FakeOpenAIApi()) // Mock OpenAIApi

        CoroutineScope(Dispatchers.IO).launch {
            val result =
                repository.fetchDailyLogs("testUserId", "2025-01-17") // Adjust userId and date
            result.onSuccess { logs ->
                logs.forEach { log ->
                    Log.d("TestLogs", log.toString())
                }
            }.onFailure { error ->
                Log.e("TestLogs", "Error: ${error.message}")
            }


            enableEdgeToEdge()
            val authViewModel: AuthViewModel by viewModels()
            setContent {
                AuriTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                                TipsScreen()
                        AuriNavigation(
                            modifier = Modifier.padding(innerPadding),
                            authViewModel = authViewModel
                        )
                    }
                }
            }
        }
    }
}

// Mock OpenAIApi for Testing
class FakeOpenAIApi : OpenAIApi {
    suspend fun getAnalysis(inputData: String, apiKey: String): String {
        return "Test response from OpenAIApi"
    }

    override suspend fun getChatCompletion(request: ChatCompletionRequest): ChatCompletionResponse {
        TODO("Not yet implemented")
    }
}



