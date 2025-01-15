package com.edu.auri.frontend.tips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.edu.auri.backend.endpoint.ApiClient
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun TipsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    // We'll create the repository inline. In practice, you might do this via DI or Hilt.
    val firestore = FirebaseFirestore.getInstance()
    val openAIApi = ApiClient.openAIApi // however you create it, e.g. a retrofit instance
    val repository = remember {
        TipsRepository(
            firestore = firestore,
            openAIApi = openAIApi
        )
    }

    // Provide an apiKey. In a real app, store it securely, not in plain code.
    val tipsViewModel = remember {
        TipsViewModel(
            repository = repository,
            apiKey = "YOUR_OPENAI_KEY_HERE"
        )
    }

    // Observe the LLM response
    val tipsText by tipsViewModel.tipsText.collectAsState()

    // Actually call the function to fetch from Firebase and hit the LLM.
    // If you only want to do this once, put it in a LaunchedEffect:
    LaunchedEffect(Unit) {
        tipsViewModel.fetchTipsAndGenerateLLMResponse()
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // The Box to display LLM response
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp)
                        .background(Color.LightGray.copy(alpha = 0.3f))
                        .padding(16.dp)
                ) {
                    Text(
                        text = tipsText,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight(700),
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // A button to re-fetch if you want to manually refresh
                Button(onClick = {
                    tipsViewModel.fetchTipsAndGenerateLLMResponse()
                }) {
                    Text("Fetch Tips & LLM Advice Again")
                }
            }
        }
    }
}

// Minimal placeholder so you can do a preview
class AuthViewModel : androidx.lifecycle.ViewModel()

@Preview(showBackground = true)
@Composable
fun TipsScreenPreview() {
    // The LLM call won't actually run in preview. Just for layout check.
    val context = LocalContext.current
    TipsScreen(
        navController = NavController(context),
        authViewModel = AuthViewModel()
    )
}