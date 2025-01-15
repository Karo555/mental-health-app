package com.edu.auri.frontend.tips
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.edu.auri.backend.endpoint.OpenAIRepository

class TipsViewModel : ViewModel() {
    private val openAIRepository = OpenAIRepository()

    private val _tipsText = MutableLiveData<String>()
    val tipsText: LiveData<String> get() = _tipsText

    fun fetchTips(userInput: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = openAIRepository.getLLMResponse(userInput, apiKey)
                _tipsText.value = response
            } catch (e: Exception) {
                _tipsText.value = "Error: ${e.message}"
            }
        }
    }
}
