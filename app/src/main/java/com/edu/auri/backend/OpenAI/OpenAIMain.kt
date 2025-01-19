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
                    // --- 1. Fetch data from Firestore ---
                    Log.d("FirestoreDebug", "Starting Firestore fetch...")
                    val userData = dataRepository.fetchUserData("users")
                    Log.d("FirestoreDebug", "Fetched user data: $userData")

                    // --- 2. Build a prompt for OpenAI ---
                    val prompt = "Based on the following user data, provide personalized tips:\n$userData"
                    Log.d("OpenAIDebug", "Prompt for OpenAI: $prompt")

                    // --- 3. Call the OpenAI API using the chat-based endpoint ---
                    val openaiResponse = openAIRepository.fetchChatCompletion(prompt)
                    Log.d("OpenAIDebug", "OpenAI response: $openaiResponse")

                    // --- 4. Update the UI on the Main thread ---
                    withContext(Dispatchers.Main) {
                        if (!openaiResponse.isNullOrEmpty()) {
                            tvTips.text = openaiResponse
                        } else {
                            tvTips.text = "Failed to retrieve a response from OpenAI."
                        }
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

