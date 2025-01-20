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

/**
 * An activity that demonstrates fetching data from Firestore and analyzing it with the OpenAI API.
 *
 * This activity performs the following tasks:
 * 1. Fetches a specific daily log from Firestore using [DataRepository].
 * 2. Constructs a prompt based on the fetched daily log.
 * 3. Calls the OpenAI chat API via [OpenAIRepository] to generate personalized tips.
 * 4. Updates the UI with the generated response.
 *
 * Firestore logging is enabled for debugging purposes.
 */
class OpenAIMain : ComponentActivity() {

    /**
     * Button to initiate fetching data.
     */
    private lateinit var btnFetchData: Button

    /**
     * TextView to display the generated tips from the OpenAI API.
     */
    private lateinit var tvTips: TextView

    /**
     * Repository instance for Firestore data operations.
     */
    private val dataRepository = DataRepository()

    /**
     * Repository instance for calling the OpenAI API.
     */
    private val openAIRepository = OpenAIRepository()

    /**
     * Called when the activity is starting.
     *
     * This method initializes the UI components, enables Firestore logging,
     * and sets a click listener on [btnFetchData] to perform the following:
     * 1. Fetch a specific daily log for a given user and date.
     * 2. Create a prompt based on the fetched daily log.
     * 3. Call the OpenAI API to generate chat-based completions.
     * 4. Update the UI with the generated tips or display error messages if any occur.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * the Bundle contains the data it most recently supplied.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable Firestore logging for debugging purposes
        FirebaseFirestore.setLoggingEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_openai)

        // Initialize UI components
        btnFetchData = findViewById(R.id.btnFetchData)
        tvTips = findViewById(R.id.tvTips)

        // Set a click listener on the fetch button
        btnFetchData.setOnClickListener {
            Log.d("FirestoreDebug", "Button pressed!")

            // Start a coroutine on the IO dispatcher for asynchronous operations
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