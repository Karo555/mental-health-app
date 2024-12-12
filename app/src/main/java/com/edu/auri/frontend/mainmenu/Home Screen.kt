package com.edu.auri.frontend.mainmenu



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.edu.auri.R


@Composable
fun HomeScreen() {
    val moodOptions = listOf("Very Happy", "Happy", "Neutral", "Sad")
    val selectedMood = remember { mutableStateOf("") }
// Navigation bar
    Scaffold(
        bottomBar = {
            BottomAppBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_analytics_24),
                            contentDescription = "Insights"
                        )
                    },
                    label = { Text("Insights") },
                    selected = false,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Gratitude") },
                    label = { Text("Gratitude") },
                    selected = false,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = false,
                    onClick = {}
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Good afternoon Tim, how are you feeling?", // add global name
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "You're doing great. Keep it up!",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .width(390.dp)
                    .height(64.dp)
                    .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp)
            ) {
                Button(
                    onClick = { /* Record mood action */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Record Mood")
                }
                Button(
                    onClick = { /* Record mood action */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("View Journal")

                }
            }

//            Row(
//                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
//                verticalAlignment = Alignment.Top,
//                modifier = Modifier
//                    .width(390.dp)
//                    .height(56.dp)
//                    .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)
//            ) {
//                Button(
//                    onClick = {/* */}
//                ) { }
//
//
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//            TextButton(onClick = { /* View history action */ }) {
//                Text("View History")
//            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Your Journal",
                style = MaterialTheme.typography.bodySmall
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Today", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        "I'm grateful for the sunny weather",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Button(
                onClick = { /* Write a note action */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Write a note")
            }

            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = { /* View all action */ }) {
                Text("View all")
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Column {
                QuickActionItem("Mood History")
                QuickActionItem("Gratitude Journal")
                QuickActionItem("Self-Care Activities")
            }
        }
    }
}

@Composable
fun QuickActionItem(actionName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = actionName,
            style = MaterialTheme.typography.bodySmall
        )
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    MaterialTheme {
        HomeScreen()
    }
}
