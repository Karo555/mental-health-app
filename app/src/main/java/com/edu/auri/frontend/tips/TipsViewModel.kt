package com.edu.auri.frontend.tips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TipsViewModel(
    private val repository: TipsRepository,
    private val apiKey: String
) : ViewModel() {

    private val _tipsText = MutableStateFlow("")   // LLM result to show on UI
    val tipsText: StateFlow<String> = _tipsText.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    fun fetchTipsAndGenerateLLMResponse() {
        viewModelScope.launch {
            try {
                // 1) Get data from Firebase
                val firebaseTips = repository.fetchTipsFromFirebase()

                // If each doc returned multiple fields, you only got one string per doc.
                // Possibly you want to combine them. For now, let's just join them with newlines:
                val combinedTips = firebaseTips.joinToString(separator = "\n")

                // 2) Build userInput from that data
                val userInput = """
                    Here are some user data fields from Firestore:
                    
                    $combinedTips

                    Please provide helpful suggestions or advice based on this data.
                """.trimIndent()

                // 3) Call LLM
                val result = repository.generateLLMResponse(userInput, apiKey)

                // 4) Update UI state
                _tipsText.value = result

            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }
}

