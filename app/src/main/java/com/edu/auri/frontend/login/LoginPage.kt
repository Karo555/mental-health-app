package com.edu.auri.frontend.login

import android.util.Log // Added import for Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.edu.auri.backend.registration.AuthState
import com.edu.auri.backend.registration.AuthViewModel
import com.edu.auri.frontend.components.EmailField
import com.edu.auri.frontend.components.PasswordField
import com.edu.auri.ui.theme.AuriTheme

// Define a TAG for logging
private const val TAG = "LoginScreen"

/**
 * Composable function that renders the Login Screen.
 *
 * The Login Screen provides UI elements for entering an email and password, and buttons to trigger
 * login or navigate to a sign-up screen. It observes authentication state changes via the
 * provided [authViewModel] to navigate or show error messages.
 *
 * @param modifier [Modifier] for this composable.
 * @param navController The [NavController] used for navigating between screens.
 * @param authViewModel The [AuthViewModel] instance that provides authentication logic.
 */
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    Log.d(TAG, "LoginScreen composed") // Log when the LoginScreen is composed

    // Hold the input states for email and password.
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    // Observe the current authentication state.
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    // React to authentication state changes.
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                Log.d(TAG, "User authenticated successfully. Navigating to home.")
                navController.navigate("home")
            }
            is AuthState.Error -> {
                val errorMessage = (authState.value as AuthState.Error).message
                Log.e(TAG, "Authentication error: $errorMessage")
                Toast.makeText(
                    context, errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> Unit
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(140.dp))
                Text(
                    text = "Login",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(28.dp))
                // Email input field.
                EmailField(email)
                Spacer(modifier = Modifier.height(28.dp))
                // Password input field.
                PasswordField(password)
                Spacer(modifier = Modifier.height(28.dp))
                // Button triggering login.
                Button(
                    onClick = {
                        Log.d(TAG, "Login button clicked with email: ${email.value}")
                        authViewModel.login(email.value, password.value)
                    }
                ) {
                    Text(text = "Login")
                }
                Spacer(modifier = Modifier.height(28.dp))
                // Button to navigate to sign up.
                TextButton(onClick = {
                    Log.d(TAG, "Navigating to sign up screen.")
                    navController.navigate("signup")
                }) {
                    Text(text = "Don't have an account? Create account")
                }
                Spacer(modifier = Modifier.height(28.dp))
            }
        }
    }
}

/**
 * Preview composable function for the Login Screen.
 *
 * This preview function wraps the Login Screen in [AuriTheme] for design-time visualization.
 */
@Composable
@Preview
fun LoginScreenPreview() {
    AuriTheme {
        // Create a default NavController for preview purposes.
        LoginScreen(
            navController = rememberNavController(),
            authViewModel = AuthViewModel()
        )
    }
}