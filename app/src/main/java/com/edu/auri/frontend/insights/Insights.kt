package com.edu.auri.frontend.insights

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
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

@Composable
fun Insights (navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        },
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues), // Apply the padding values passed as a parameter
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .width(390.dp)
                    .height(64.dp)
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Center, // Center horizontally
                verticalAlignment = Alignment.CenterVertically, // Center vertically
            ) {
                Text(
                    text = "Home",
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 23.sp,
                        fontWeight = FontWeight(700),
                        textAlign = TextAlign.Center // Center the text within the Text component
                    )
                )
            }
        }
    }
}

