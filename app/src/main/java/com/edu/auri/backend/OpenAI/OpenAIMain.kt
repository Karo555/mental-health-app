package com.edu.auri.backend.OpenAI

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.edu.auri.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

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
     * Firebase Authentication instance.
     */
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Firestore instance.
     */
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    /**
     * Gson instance for serializing the daily log.
     */
    private val gson = Gson()

    /**
     * Called when the activity is starting.
     *
     * This method initializes the UI components, enables Firestore logging,
     * and sets a click listener on [btnFetchData] to perform the following:
     * 1. Fetch a specific daily log for the currently logged-in user and a specified date.
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
            Log.d("FirestoreDebug", "Fetch Data button pressed!")

            // Start a coroutine on the IO dispatcher for asynchronous operations
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // --- 1. Retrieve Current User's UID ---
                    val currentUser = auth.currentUser
                    if (currentUser == null) {
                        Log.e("OpenAIMain", "No user is currently logged in.")
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@OpenAIMain,
                                "Please log in to fetch your daily logs.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        return@launch
                    }

                    val userId = currentUser.uid
                    val userEmail = currentUser.email ?: "Unknown Email"
                    Log.d("FirestoreDebug", "Current user ID: $userId, Email: $userEmail")

                    // --- 2. Specify the Date for the Daily Log ---
                    // You can modify this to fetch logs for different dates as needed.
                    // For dynamic dates, consider adding a date picker or other UI elements.
                    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(System.currentTimeMillis())
                    Log.d("FirestoreDebug", "Fetching daily log for date: $date")

                    // --- 3. Fetch Specific Daily Log from Firestore ---
                    Log.d(
                        "FirestoreDebug",
                        "Fetching daily log for user: $userId on date: $date"
                    )
                    val dailyLog = dataRepository.fetchDailyLog(userId, date)
                    Log.d("FirestoreDebug", "Fetched daily log: $dailyLog")

                    // --- 4. Serialize Daily Log to JSON for Prompt ---
                    val dailyLogJson = if (dailyLog != null) {
                        gson.toJson(dailyLog)
                    } else {
                        "No daily log data available for date: $date."
                    }
                    Log.d("FirestoreDebug", "Serialized Daily Log JSON: $dailyLogJson")

                    // --- 5. Build a Prompt for OpenAI Using the Fetched Daily Log ---
                    val prompt = if (dailyLog != null) {
                        "Based on the following daily log, provide personalized tips:\nDaily Log: $dailyLogJson"
                    } else {
                        "No daily log data available for date: $date."
                    }
                    Log.d("OpenAIDebug", "Prompt for OpenAI: $prompt")

                    // --- 6. Call the OpenAI API Using the Chat-Based Endpoint ---
                    val openaiResponse = openAIRepository.fetchChatCompletion(prompt)
                    Log.d("OpenAIDebug", "OpenAI response: $openaiResponse")

                    // --- 7. Update the UI on the Main Thread ---
                    withContext(Dispatchers.Main) {
                        tvTips.text = openaiResponse ?: "Failed to retrieve a response from OpenAI."
                        Toast.makeText(
                            this@OpenAIMain,
                            "Data fetched & analyzed",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: Exception) {
                    Log.e("OpenAIMain", "Error during Firestore/OpenAI processing", e)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@OpenAIMain,
                            "Error occurred: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}