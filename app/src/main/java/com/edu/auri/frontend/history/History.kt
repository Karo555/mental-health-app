package com.edu.auri.frontend.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.edu.auri.frontend.components.BottomBar

/**
 *
 */
/**
 *
 */
@Composable
fun HistoryScreen(navController: NavController){
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        },
    )
    { paddingValues ->
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
            )
        }
    }
}