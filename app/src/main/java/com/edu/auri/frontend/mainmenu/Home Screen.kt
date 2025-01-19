package com.edu.auri.frontend.mainmenu

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.edu.auri.R
import com.edu.auri.ui.theme.AuriTheme
import com.edu.auri.backend.registration.AuthViewModel

/**
 * Composable function for rendering the main home screen.
 *
 * This screen includes a bottom navigation bar, welcome message, mood selection items,
 * a journal preview card, quick action buttons for writing a note or completing an overview,
 * and a temporary sign-out button.
 *
 * @param modifier [Modifier] to be applied to the layout.
 * @param navController The [NavController] for navigating between screens.
 * @param authViewModel The [AuthViewModel] instance that manages authentication actions such as signing out.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    // Navigation bar with multiple navigation items.
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
                .fillMaxWidth()
                .padding(paddingValues), // Apply padding values from the Scaffold.
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .width(390.dp)
                    .height(64.dp)
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Center, // Center horizontally
                verticalAlignment = Alignment.CenterVertically // Center vertically
            ) {
                Text(
                    text = "Home",
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 23.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF141C24),
                        textAlign = TextAlign.Center
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp)) // Space between elements

            Text(
                text = "Good afternoon Tim, how are you feeling?",
                // TODO: Use global username and update welcome message based on time of day.
                style = TextStyle(
                    fontSize = 32.sp,
                    lineHeight = 40.sp,
                    fontWeight = FontWeight(700),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(44.dp))
            Text(
                text = "What is your current mood?",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Row(
                modifier = Modifier
                    .width(390.dp)
                    .height(64.dp)
                    .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.Top,
            ) {
                MoodItem("Happy")
                MoodItem("Neutral")
                MoodItem("Angry")
                MoodItem("Sad")
            }

            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .width(390.dp)
                    .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.Absolute.Left,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your Journal \uD83D\uDCD3 ",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Spacer(modifier = Modifier.height(15.dp))
            Box(
                modifier = Modifier.width(390.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .width(380.dp)
                        .height(70.dp),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Today", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            "I'm grateful for the sunny weather",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .width(390.dp)
                    .height(64.dp)
                    .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.Top,
            ) {
                Button(
                    onClick = { /* Record mood action */ },
                    modifier = Modifier.width(310.dp),
                ) {
                    Text("Write a note")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .width(390.dp)
                    .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.Absolute.Left,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Fill in the day overview \uD83C\uDF16 ",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Row(
                modifier = Modifier
                    .width(390.dp)
                    .height(64.dp)
                    .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.Top,
            ) {
                Button(
                    onClick = { /* Record mood action */ },
                    Modifier.width(310.dp)
                ) {
                    Text("Complete today's overview")
                }
            }
            Row(
                modifier = Modifier
                    .width(390.dp)
                    .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.Absolute.Left,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { authViewModel.signOut() },
                    Modifier.width(310.dp)
                ) {
                    Text("Sign out TEMPORARY")
                }
            }
            /*
            Column {
                QuickActionItem("Mood History")
                QuickActionItem("Gratitude Journal")
                QuickActionItem("Self-Care Activities")
            }
            */
        }
    }
}

/**
 * Composable function that represents a mood selection button.
 *
 * This button displays an emoji corresponding to the provided mood label. The mapping between the mood
 * and its emoji is defined within the function.
 *
 * @param mood A [String] representing the mood (e.g., "Happy", "Angry", "Neutral", "Sad").
 */
@Composable
fun MoodItem(mood: String) {
    val moodDictionary = mapOf(
        "Happy" to "\uD83D\uDE04",
        "Angry" to "\uD83D\uDE24",
        "Neutral" to "\uD83D\uDE42",
        "Sad" to "\uD83D\uDE41"
    )
    Button(
        onClick = { /* Record mood action */ },
        colors = ButtonDefaults.buttonColors(mood.let { Color(0xFFFFFFFF) }),
        modifier = Modifier.width(76.dp)
    ) {
        Text(
            text = "${moodDictionary[mood]}",
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 23.sp,
                fontWeight = FontWeight(700),
                textAlign = TextAlign.Center
            )
        )
    }
}

/**
 * Composable function representing a quick action item in a row layout.
 *
 * This function displays a text label along with an arrow icon indicating
 * that the item can be tapped or navigated to for further details.
 *
 * @param actionName A [String] representing the name of the quick action.
 */
@Composable
fun QuickActionItem(actionName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = actionName,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 23.sp,
                fontWeight = FontWeight(700),
                textAlign = TextAlign.Start
            )
        )
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
    }
}

/**
 * Preview function for the HomeScreen composable.
 *
 * This preview function wraps the HomeScreen in [AuriTheme] for a design-time preview.
 */
@Composable
@Preview
fun HomeScreenPreview() {
    AuriTheme {
        HomeScreen(
            navController = rememberNavController(),
            authViewModel = AuthViewModel()
        )
    }
}

//@Preview
//@Composable
//fun PreviewHomeScreen() {
//    AuriTheme {
//        HomeScreen(modifier = Modifier, navController = NavController(LocalContext.current))
//    }
//}
