package com.edu.auri.frontend.tips

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.edu.auri.backend.registration.AuthViewModel

@Composable
fun TipsScreen(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    val tipsViewModel: TipsViewModel = viewModel()
    val tipsText by tipsViewModel.tipsText.observeAsState("")

    // Call fetchTips with appropriate userInput and apiKey
    // For example, you can call it in response to a button click or when the screen is first displayed
    // tipsViewModel.fetchTips("user input", "api key")

    // UI code to display the tipsText
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = tipsText,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight(700),
                    ),
                    textAlign = TextAlign.Center
                )
                // Other UI elements
            }
        }
    }
}