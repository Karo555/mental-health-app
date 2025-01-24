package com.edu.auri.frontend.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun HistoryScreen(navController: NavController) {
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ){
                item {
                    Row(
                        modifier = Modifier
                            .width(390.dp)
                            .height(64.dp)
                            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "History",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 23.sp,
                                fontWeight = FontWeight(700),
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryScreenDemo(navController: NavController) {
    HistoryScreen(navController = navController)
}