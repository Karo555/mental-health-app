package com.edu.auri.frontend.tips
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log


class TipsViewModel(
    private val repository: TipsRepository,
    private val apiKey: String  // or fetch from a secure location
) : ViewModel() {

    // Store fetched tips
    private val _tipsFlow = MutableStateFlow<List<String>>(emptyList())
    val tipsFlow = _tipsFlow.asStateFlow()

    // Store the LLM response
    private val _llmResponse = MutableStateFlow<String?>(null)
    val llmResponse = _llmResponse.asStateFlow()


    // Any error states or loading states
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    /**
     * 1) Fetch tips from Firebase
     * 2) Use them as userInput for the LLM
     * 3) Store LLM response in _llmResponse
     */
    fun fetchTipsAndGenerateLLMResponse() {
        viewModelScope.launch {
            try {
                // 1. Get data from Firebase
                val fetchedTips = repository.fetchTipsFromFirebase()
                _tipsFlow.value = fetchedTips

                // 2. Build a prompt or input from the tips
                // Example: Just join them with newlines or bullet points
                val userInput = buildString {
                    append("Here are some tips I have: \n")
                    fetchedTips.forEachIndexed { index, tip ->
                        append("${index + 1}. $tip\n")
                    }
                    append("Please analyze these tips and give me additional helpful advice.\n")
                }

                // 3. Send userInput to the LLM
                val result = repository.generateLLMResponse(userInput, apiKey)

                // 4. Update the LLM response
                _llmResponse.value = result

                val llmText = "This is what the LLM returned"
                Log.d("TipsScreen", "LLM Response: $llmText")


            } catch(e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    /**
     * Quick test method: forcibly set some test data
     * and call the LLM.
     * This helps confirm the LLM flow without real Firebase data.
     */
    fun testLLMWithMockData() {
        viewModelScope.launch {
            try {
                val mockTips = listOf("Stay hydrated", "Take breaks", "Exercise daily")
                // we won't set _tipsFlow here so it doesn't confuse real data
                val userInput = """
                    I have the following tips:
                    ${mockTips.joinToString("\n")}

                    Please provide additional suggestions.
                """.trimIndent()

                val result = repository.generateLLMResponse(userInput, apiKey)
                _llmResponse.value = "[TEST LLM RESPONSE] $result"
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }
}



