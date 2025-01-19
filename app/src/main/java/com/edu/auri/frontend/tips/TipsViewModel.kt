package com.edu.auri.frontend.tips

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class TipsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<TipsUiState>(TipsUiState.Idle)
    val uiState: StateFlow<TipsUiState> = _uiState.asStateFlow()

    fun generateTips() {
        // Simulate loading, success, and error states for testing
        CoroutineScope(Dispatchers.IO).launch {
            _uiState.value = TipsUiState.Loading
            try {
                // Simulate a delay to represent API call
                delay(2000)
                // Simulate a success response
                _uiState.value = TipsUiState.Success("Here are your personalized tips!")
            } catch (e: Exception) {
                // Simulate an error response
                _uiState.value = TipsUiState.Error("Failed to generate tips.")
            }
        }
    }
}