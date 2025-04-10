package com.edu.auri.frontend.sign_up

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.livedata.observeAsState
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
import com.edu.auri.backend.registration.AuthState
import com.edu.auri.backend.registration.AuthViewModel
import com.edu.auri.frontend.components.EmailField
import com.edu.auri.frontend.components.NameField
import com.edu.auri.frontend.components.PasswordField
import com.edu.auri.ui.theme.AuriTheme

/**
 * Composable function that renders the registration screen.
 *
 * The RegistrationScreen allows a user to create an account by entering their name, email, and password.
 * It observes the [AuthViewModel.authState] to react to authentication events. When the user is
 * successfully authenticated, the screen navigates to the home screen. Additionally, it provides
 * a navigation option to the login screen.
 *
 * @param modifier A [Modifier] for styling or layout adjustments.
 * @param navController The [NavController] used for navigation between screens.
 * @param authViewModel The [AuthViewModel] that manages authentication-related actions.
 */
@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    // Remembered states for user input fields.
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var name = remember { mutableStateOf("") }

    // Observe the authentication state from the ViewModel.
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    // React to changes in the authentication state.
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate("home")
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceVariant
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
                    text = "Create account",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(28.dp))
                NameField(name)
                Spacer(modifier = Modifier.height(50.dp))
                EmailField(email)
                Spacer(modifier = Modifier.height(50.dp))
                PasswordField(password)
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = { authViewModel.signUp(email.value, password.value, name.value) },
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp),
                ) {
                    Text(text = "Sign Up")
                }
                Spacer(modifier = Modifier.height(50.dp))
                TextButton(onClick = { navController.navigate("login") }) {
                    Text(text = "Already have an account? Login")
                }
            }
        }
    }
}

/**
 * Preview composable function for the RegistrationScreen.
 *
 * This function wraps the RegistrationScreen within [AuriTheme] for design-time visualization.
 * Note: The preview creates a dummy [NavController] and a new instance of [AuthViewModel] for preview purposes.
 */
@Preview
@Composable
fun RegistrationScreenPreview() {
    AuriTheme {
        RegistrationScreen(
            modifier = Modifier,
            navController = NavController(LocalContext.current),
            authViewModel = AuthViewModel()
        )
    }
}