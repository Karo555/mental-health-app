package com.edu.auri.frontend.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.edu.auri.backend.dailylogs.LogDailyViewModel
import com.edu.auri.backend.registration.AuthState
import com.edu.auri.backend.registration.AuthViewModel
import com.edu.auri.frontend.components.BottomBar
import java.time.LocalDate
import java.util.Calendar


/**

 * The home screen of the app.
 * @param modifier The modifier for the composable.
 * @param navController The navigation controller for the app.
 * @param authViewModel The authentication view model for the app.
 */

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, navController: NavController,
    authViewModel: AuthViewModel, viewModel: LogDailyViewModel
) {
    val dailyLogs by viewModel.dailyLogs.collectAsState()
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    // Navigation bar
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
                            text = "Home",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 23.sp,
                                fontWeight = FontWeight(700),
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    Text(
                        text = "${getGreeting()}, how are you feeling?",
                        style = TextStyle(
                            fontSize = 32.sp,
                            lineHeight = 40.sp,
                            fontWeight = FontWeight(700),
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)

                    )
                }


                item { Spacer(modifier = Modifier.height(44.dp)) }
                item {
                    Row(
                        modifier = Modifier
                            .width(390.dp)
                            .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
                        horizontalArrangement = Arrangement.Absolute.Left,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Fill in the day overview ",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
                item { DayOverviewButton(viewModel, navController) }
//
                item { Spacer(modifier = Modifier.height(24.dp)) }


                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
                        horizontalArrangement = Arrangement.Absolute.Left,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Notes",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(14.dp)) }

                item {
                    Box(
                        modifier = Modifier
                            .width(390.dp)
                            .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),

                        ) {
                        Card(
                            modifier = Modifier
                                .width(380.dp)
                                .height(70.dp)
                                .clickable { navController.navigate("notes") },
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
                }

                item { Spacer(modifier = Modifier.height(14.dp)) }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
                        horizontalArrangement = Arrangement.Absolute.Left,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Your journal",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
                item { Spacer(modifier = Modifier.height(15.dp)) }
                item {
                    Box(
                        modifier = Modifier
                            .width(390.dp)
                            .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),

                        ) {
                        Card(
                            modifier = Modifier
                                .width(380.dp)
                                .height(70.dp)
                                .clickable { navController.navigate("notes journal") },
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Journal", style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    "I'm grateful for the sunny weather",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }


            }
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

fun getGreeting(): String {
    val currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (currentTime) {
        in 6..11 -> "Good morning"
        in 12..17 -> "Good afternoon"
        else -> "Good evening"
    }
}



/**
 * Composable function that displays a button for day overview.
 *
 * This function checks if today's log exists and displays either a completed day overview or an active day overview.
 *
 * @param viewModel The view model for managing daily logs data.
 * @param navController The navigation controller for handling screen navigation.
 */
@Composable
fun DayOverviewButton(viewModel: LogDailyViewModel, navController: NavController) {

    val dailyLogs by viewModel.dailyLogs.collectAsState()
    val today = remember { LocalDate.now().toString() }

    // Continuously check if today's log exists
    val hasCompletedToday by remember(dailyLogs) {
        derivedStateOf { dailyLogs.containsKey(today) }
    }

    if (hasCompletedToday) {
        CompletedDayOverview()
    } else {
        DailyOverviewActive(navController)
    }
}

/**
 * Composable function that displays a completed day overview.
 *
 * This function displays a card with a congratulatory message when the user has completed today's overview.
 */
@Composable
fun CompletedDayOverview() {
    Box(
        modifier = Modifier
            .width(390.dp)
            .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),

        ) {

        Card(
            modifier = Modifier
                .width(380.dp)
                .height(90.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Great job! You've completed today's overview  \uD83C\uDF16\n",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

}

/**
 * Composable function that displays an active day overview.
 *
 * This function displays a row with a button that navigates to the day overview screen when clicked.
 *
 * @param navController The navigation controller for handling screen navigation.
 */
@Composable
fun DailyOverviewActive(navController: NavController) {
    // Create a row with a fixed width and height, and padding on all sides
    Row(
        modifier = Modifier
            .width(390.dp) // Set the width of the row
            .height(64.dp) // Set the height of the row
            .padding( // Add padding to the row
                start = 16.dp, // Padding on the start side
                top = 12.dp, // Padding on the top side
                end = 16.dp, // Padding on the end side
                bottom = 12.dp // Padding on the bottom side
            ),
        // Arrange the children of the row horizontally with a spaced by arrangement
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        // Align the children of the row vertically to the top
        verticalAlignment = Alignment.Top,
    ) {
        // Create a button that navigates to the day overview screen when clicked
        Button(
            onClick = { navController.navigate("dayoverview") }, // Navigate to the day overview screen
            Modifier.width(310.dp) // Set the width of the button
        ) {
            // Display the text on the button
            Text("Complete today's overview  \uD83C\uDF16")
        }
    }
}