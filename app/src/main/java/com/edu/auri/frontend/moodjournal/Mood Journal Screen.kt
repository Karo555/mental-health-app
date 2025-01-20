package com.edu.auri.frontend.moodjournal

import androidx.compose.ui.draw.clip
import com.edu.auri.backend.moodjournal.Mood
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.edu.auri.frontend.components.BottomBar
import com.edu.auri.ui.theme.AuriTheme
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MoodJournalScreenDemo(moodsList: List<Mood>, navController: NavController) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues -> // Correct usage of paddingValues here
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // Apply padding from Scaffold to Surface
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Journal",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 23.sp,
                                fontWeight = FontWeight.Bold, // Simplified fontWeight
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
                items(moodsList) { mood ->
                    MoodCard(mood)
                }
            }
        }
    }
}


@Composable
fun MoodCard(mood: Mood) {
    val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
    val formattedDate = dateFormat.format(mood.timestamp?.toDate() ?: return)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium.copy(CornerSize(12.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Mood: ${mood.mood}",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Notes: ${mood.notes}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Date: $formattedDate",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Preview
@Composable
fun PreviewMoodJournalScreenDemo() {
    val demoMoods = listOf(
        Mood("Happy", "Had a fantastic day!", Timestamp.now()),
        Mood("Anxious", "App not working", Timestamp.now()),
        Mood("Excited", "Looking forward to the weekend!", Timestamp.now())
    )
    AuriTheme {MoodJournalScreenDemo(moodsList = demoMoods, navController = NavController(LocalContext.current))}
}

