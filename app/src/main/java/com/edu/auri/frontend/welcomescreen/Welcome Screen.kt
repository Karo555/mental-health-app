package com.edu.auri.frontend.welcomescreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.edu.auri.R
import com.edu.auri.backend.registration.AuthState
import com.edu.auri.backend.registration.AuthViewModel

/**
 * Composable function that renders the welcome screen.
 *
 * The WelcomeScreen displays a background image, a welcome message, a subtitle,
 * and buttons for sign-up and log-in. It observes the authentication state from
 * the AuthViewModel and navigates to the home screen when the user is authenticated.
 *
 * @param modifier A Modifier for styling or layout adjustments.
 * @param navController The NavController used for navigation between screens.
 * @param authViewModel The AuthViewModel that manages authentication-related actions.
 */
@Composable
fun WelcomeScreen(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {

    val authState = authViewModel.authState
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate("home")
            else -> Unit
        }
    }
    // Use Box to layer elements
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.backgroundupdate), // Replace with your image resource
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(140.dp))
                Text(
                    text = "Welcome to Auri",
                    style = TextStyle(
                        fontSize = 42.sp,
                        lineHeight = 40.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF141C24),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp) // Add horizontal padding for alignment
                )
                // Welcome Text

                Spacer(modifier = Modifier.height(28.dp))

                // Subtitle
                Text(
                    text = "Mental health is not just the absence of mental illness. " +
                            "It’s emotional, physical, and social well-being\n" + "  .   .   ."
                            +"\nMeghan McCain",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF141C24),
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(350.dp))

                // Sign-up Button
                Button(
                    onClick = { navController.navigate("signup") },
                    modifier = Modifier.width(200.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF141C24)),
                ) {
                    Text(text = "Sign up",
                    style = TextStyle(
                        color = Color(0xFFFFFFFF),
                    )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Log-in Text
                Text(
                    text = "Already member?",
                    color = Color(0xFF141C24),
                    style = TextStyle(
                        fontWeight = FontWeight(600),
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Normal,
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate("login") },
                    modifier = Modifier
                        .width(200.dp),
                    colors = ButtonDefaults.buttonColors(Color(0XFFFFFFFF)),
                ) {
                    Text(
                        text = "Log in",
                        color = Color(0xFF141C24),

                    )

                }
            }
        }

    }
}