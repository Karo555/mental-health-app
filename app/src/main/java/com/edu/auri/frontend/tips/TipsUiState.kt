package com.edu.auri.frontend.tips

sealed class TipsUiState {
    object Idle : TipsUiState()
    object Loading : TipsUiState()
    data class Success(val message: String) : TipsUiState()
    data class Error(val error: String) : TipsUiState()
}