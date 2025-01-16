package com.edu.auri.frontend.mainmenu



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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.edu.auri.backend.registration.AuthState
import com.edu.auri.backend.registration.AuthViewModel
import com.edu.auri.frontend.components.BottomBar
import java.util.Calendar

/**
 * The home screen of the app.
 * @param modifier The modifier for the composable.
 * @param navController The navigation controller for the app.
 * @param authViewModel The authentication view model for the app.
 */

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController,
               authViewModel: AuthViewModel) {
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

            Spacer(modifier = Modifier.height(16.dp)) // Add space between elements

            Text(
                text = "${getGreeting()}, how are you feeling?", //TODO Global username and  welcome message depending on time of day
                style = TextStyle(
                    fontSize = 32.sp,
                    lineHeight = 40.sp,
                    fontWeight = FontWeight(700),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(horizontal = 16.dp) // Add horizontal padding for alignment
            )

            Spacer(modifier = Modifier.height(44.dp)) // Add space between elements
            Text(
                text = "What is your current mood?",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 16.dp) // Add horizontal padding for alignment
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
            Spacer(modifier = Modifier.height(15.dp)) // Add space between elements
            Box(
                modifier = Modifier
                    .width(390.dp),
                contentAlignment = Alignment.Center // Centers content both horizontally and vertically
            ) {
                Card(
                    modifier = Modifier
                        .width(380.dp)
                        .height(70.dp),
                ) { // Add a Card component
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
            Spacer(modifier = Modifier.height(24.dp)) // Add space between elements
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
            }// Add space between elements
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
            Row (
                modifier = Modifier
                    .width(390.dp)
                    .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.Absolute.Left,
                verticalAlignment = Alignment.CenterVertically
            )  {
                Button(
                    onClick = {authViewModel.signOut()},
                    Modifier.width(310.dp)
                ) {
                    Text("Sign out TEMPORARY")

                }
            }

            /*Column {
                QuickActionItem("Mood History")
                QuickActionItem("Gratitude Journal")
                QuickActionItem("Self-Care Activities")
            }*/
        }


    }
}


// Navigation bar item
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
        colors = ButtonDefaults.buttonColors(mood.let {Color(0xFFFFFFFF) }),
        modifier = Modifier.width(76.dp)
    ) {
        Text( text = "${moodDictionary[mood]}",
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 23.sp,
                fontWeight = FontWeight(700),
                textAlign = TextAlign.Center
            )
        )
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
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 23.sp,
                fontWeight = FontWeight(700),
                textAlign = TextAlign.Start
            ),
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

//@Preview
//@Composable
//fun PreviewHomeScreen() {
//    AuriTheme {
//        HomeScreen(modifier = Modifier, navController = NavController(LocalContext.current))
//    }
//}
