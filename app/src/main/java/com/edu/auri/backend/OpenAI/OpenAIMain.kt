package com.edu.auri.backend.OpenAI

import com.edu.auri.R
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OpenAIMain : ComponentActivity() {

    private lateinit var btnFetchData: Button
    private lateinit var tvTips: TextView

    // Create instances of your repositories
    private val dataRepository = DataRepository()
    private val openAIRepository = OpenAIRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable Firestore logging for debugging purposes
        FirebaseFirestore.setLoggingEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_openai)

        btnFetchData = findViewById(R.id.btnFetchData)
        tvTips = findViewById(R.id.tvTips)

        btnFetchData.setOnClickListener {
            Log.d("FirestoreDebug", "Button pressed!")

            // Start a coroutine on the IO dispatcher
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // --- 1. Fetch specific daily log from Firestore ---
                    // Replace these with your actual user document ID and desired date string.
                    val userId = "pD2HsHYApKaDJdAhOaQncBvCIym2"
                    val date = "2024-12-30"
                    Log.d("FirestoreDebug", "Fetching daily log for user: $userId on date: $date")
                    val dailyLog = dataRepository.fetchDailyLog(userId, date)
                    Log.d("FirestoreDebug", "Fetched daily log: $dailyLog")

                    // --- 2. Build a prompt for OpenAI using the fetched daily log ---
                    val prompt = if (dailyLog != null) {
                        "Based on the following daily log, provide personalized tips:\nDaily Log: $dailyLog"
                    } else {
                        "No daily log data available for user: $userId on date: $date."
                    }
                    Log.d("OpenAIDebug", "Prompt for OpenAI: $prompt")

                    // --- 3. Call the OpenAI API using the chat-based endpoint ---
                    val openaiResponse = openAIRepository.fetchChatCompletion(prompt)
                    Log.d("OpenAIDebug", "OpenAI response: $openaiResponse")

                    // --- 4. Update the UI on the Main thread ---
                    withContext(Dispatchers.Main) {
                        tvTips.text = openaiResponse ?: "Failed to retrieve a response from OpenAI."
                        Toast.makeText(this@OpenAIMain, "Data fetched & analyzed", Toast.LENGTH_LONG).show()
                    }

                } catch (e: Exception) {
                    Log.e("OpenAIMain", "Error during Firestore/OpenAI processing", e)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@OpenAIMain, "Error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}


