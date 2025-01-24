// File: InsightsScreen.kt
package com.edu.auri.frontend.insights

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.edu.auri.backend.insights.InsightsViewModel
import com.edu.auri.frontend.components.BottomBar

@Composable
fun InsightsScreen(navController: NavController, viewModel: InsightsViewModel = viewModel()) {

    val isLoading by viewModel.isLoading.collectAsState()
    val openAIResponse by viewModel.openAIResponse.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Header
            Text(
                text = "Insights",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Description
            Text(
                text = "Here are some insights based on your daily logs:",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Button to fetch insights
            Button(
                onClick = {
                    viewModel.fetchInsights { message ->
                        // Show a Toast message directly using the captured context
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                    }
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Get your insights")
            }

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
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .heightIn(max = 300.dp) // Set a maximum height
                                .verticalScroll(rememberScrollState()) // Make content scrollable
                        ) {
                            Text(
                                text = openAIResponse!!,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Start
                                ),
                                modifier = Modifier.fillMaxWidth()
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