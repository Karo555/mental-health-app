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
 * Composable function that renders the History screen.
 *
 * This screen displays a Scaffold with a bottom bar and a Surface that fills the available space.
 * The Surface has a background color based on the MaterialTheme color scheme.
 *
 * @param navController The NavController used for navigation between screens.
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