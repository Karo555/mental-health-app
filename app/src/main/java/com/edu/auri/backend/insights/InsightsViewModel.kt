package com.edu.auri.backend.insights

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.auri.backend.OpenAI.DataRepository
import com.edu.auri.backend.OpenAI.OpenAIRepository
import com.edu.auri.backend.dailylogs.DailyLog
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class InsightsViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _openAIResponse = MutableStateFlow<String?>(null)
    val openAIResponse: StateFlow<String?> = _openAIResponse

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val dataRepository = DataRepository()
    private val openAIRepository = OpenAIRepository()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val gson = Gson()

    fun fetchInsights(onToast: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _openAIResponse.value = null

            try {
                val currentUser = auth.currentUser
                if (currentUser == null) {
                    Log.e("InsightsViewModel", "No user is currently logged in.")
                    onToast("Please log in to fetch your daily logs.")
                    _isLoading.value = false
                    return@launch
                }

                val userId = currentUser.uid
                val userEmail = currentUser.email ?: "Unknown Email"
                Log.d("InsightsViewModel", "Current user ID: $userId, Email: $userEmail")

                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .format(System.currentTimeMillis())
                Log.d("InsightsViewModel", "Fetching daily log for date: $date")

                val dailyLog: DailyLog? = withContext(Dispatchers.IO) {
                    dataRepository.fetchDailyLog(userId, date)
                }
                Log.d("InsightsViewModel", "Fetched daily log: $dailyLog")

                val dailyLogJson = if (dailyLog != null) {
                    gson.toJson(dailyLog)
                } else {
                    "No daily log data available for date: $date."
                }
                Log.d("InsightsViewModel", "Serialized Daily Log JSON: $dailyLogJson")

                val prompt = if (dailyLog != null) {
                    "Based on the following daily log, provide personalized tips:\n" +
                            "Daily Log: $dailyLogJson (Please finish the sentence before stopping, and avoid any bold or other text formatting.).\n" +
                            "Open the Json file with DailyLog provided earlier, if DailyLog is empty, kindly remind the user to log the data first and come back later for insights."
                } else {
                    "No daily log data available for date: $date. Tell user to first log the data and come back later for insights. Do not use current date from daily log. Refer to it as 'today'."
                }
                Log.d("InsightsViewModel", "Prompt for OpenAI: $prompt")

                val response: String? = withContext(Dispatchers.IO) {
                    openAIRepository.fetchChatCompletion(prompt)
                }
                Log.d("InsightsViewModel", "OpenAI response: $response")

                if (response != null) {
                    _openAIResponse.value = response
                } else {
                    _openAIResponse.value = "Failed to retrieve a response from OpenAI."
                }

            } catch (e: Exception) {
                Log.e("InsightsViewModel", "Error during Firestore/OpenAI processing", e)
                _errorMessage.value = "Error occurred: ${e.message}"
                onToast("Error occurred: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}