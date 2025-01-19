// 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
package com.edu.auri.frontend.tips


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun TipsScreen(modifier: Modifier = Modifier) {
    val viewModel: TipsViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is TipsUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is TipsUiState.Success -> {
                    Text(
                        text = (state as TipsUiState.Success).message,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray.copy(alpha = 0.2f))
                            .padding(16.dp)
                    )
                }
                is TipsUiState.Error -> {
                    Text(
                        text = (state as TipsUiState.Error).error,
                        color = Color.Red,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                is TipsUiState.Idle -> {
                    Text(
                        text = "Press the button to generate tips!",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            // Generate Tips Button
            Button(
                onClick = { viewModel.generateTips() },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
            ) {
                Text(text = "Generate Tips")
            }
        }
    }
}