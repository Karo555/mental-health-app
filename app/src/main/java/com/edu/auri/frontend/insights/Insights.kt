package com.edu.auri.frontend.insights

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.edu.auri.frontend.components.BottomBar
import com.edu.auri.backend.OpenAI.DataRepository
import com.edu.auri.backend.OpenAI.OpenAIRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun Insights(navController: NavController) {

    // State variables to manage loading, response, and errors
    var isLoading by remember { mutableStateOf(false) }
    var openAIResponse by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Instantiate repositories
    val dataRepository = remember { DataRepository() }
    val openAIRepository = remember { OpenAIRepository() }

    // Firebase Auth instance
    val auth = FirebaseAuth.getInstance()

    // Gson instance for serialization
    val gson = remember { Gson() }

    // Coroutine scope for launching asynchronous tasks
    val coroutineScope = rememberCoroutineScope()

    // Local context for displaying Toast messages
    val context = LocalContext.current

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            // Header Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Insights",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
            }

            // LazyColumn for scrolling content
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                item { Spacer(modifier = Modifier.height(46.dp)) }
                item { Text("Here are some insights based on your daily logs:") }
                item { Spacer(modifier = Modifier.height(46.dp)) }
                item {
                    // Button to fetch insights
                    Button(
                        onClick = {
                            // Launch a coroutine to perform network operations
                            coroutineScope.launch {
                                // Set loading state
                                isLoading = true
                                errorMessage = null
                                openAIResponse = null

                                try {
                                    // 1. Retrieve Current User's UID
                                    val currentUser = auth.currentUser
                                    if (currentUser == null) {
                                        Log.e("Insights", "No user is currently logged in.")
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(
                                                context,
                                                "Please log in to fetch your daily logs.",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                        isLoading = false
                                        return@launch
                                    }

                                    val userId = currentUser.uid
                                    val userEmail = currentUser.email ?: "Unknown Email"
                                    Log.d("Insights", "Current user ID: $userId, Email: $userEmail")

                                    // 2. Specify the Date for the Daily Log (Current Date)
                                    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                        .format(System.currentTimeMillis())
                                    Log.d("Insights", "Fetching daily log for date: $date")

                                    // 3. Fetch Specific Daily Log from Firestore
                                    Log.d("Insights", "Fetching daily log for user: $userId on date: $date")
                                    val dailyLog = dataRepository.fetchDailyLog(userId, date)
                                    Log.d("Insights", "Fetched daily log: $dailyLog")

                                    // 4. Serialize Daily Log to JSON for Prompt
                                    val dailyLogJson = if (dailyLog != null) {
                                        gson.toJson(dailyLog)
                                    } else {
                                        "No daily log data available for date: $date."
                                    }
                                    Log.d("Insights", "Serialized Daily Log JSON: $dailyLogJson")

                                    // 5. Build a Prompt for OpenAI Using the Fetched Daily Log
                                    val prompt = if (dailyLog != null) {
                                        "Based on the following daily log, provide personalized tips:\nDaily Log: $dailyLogJson"
                                    } else {
                                        "No daily log data available for date: $date."
                                    }
                                    Log.d("Insights", "Prompt for OpenAI: $prompt")

                                    // 6. Call the OpenAI API Using the Chat-Based Endpoint
                                    val response = openAIRepository.fetchChatCompletion(prompt)
                                    Log.d("Insights", "OpenAI response: $response")

                                    // 7. Update the UI with the Response
                                    withContext(Dispatchers.Main) {
                                        if (response != null) {
                                            openAIResponse = response
                                        } else {
                                            openAIResponse = "Failed to retrieve a response from OpenAI."
                                        }
                                    }

                                } catch (e: Exception) {
                                    Log.e("Insights", "Error during Firestore/OpenAI processing", e)
                                    withContext(Dispatchers.Main) {
                                        errorMessage = "Error occurred: ${e.message}"
                                        Toast.makeText(
                                            context,
                                            "Error occurred: ${e.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                } finally {
                                    // Reset loading state
                                    withContext(Dispatchers.Main) {
                                        isLoading = false
                                    }
                                }
                            }
                        },
                        enabled = !isLoading, // Disable button while loading
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Get your insights")
                    }
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    // Display Loading Indicator, Response, or Error Message
                    when {
                        isLoading -> {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator()
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Loading...")
                            }
                        }
                        openAIResponse != null -> {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = openAIResponse!!,
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Start
                                        )
                                    )
                                }
                            }
                        }
                        errorMessage != null -> {
                            Text(
                                text = errorMessage!!,
                                color = Color.Red,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                        else -> {
                            // Placeholder or nothing
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}