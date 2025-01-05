package com.edu.auri.frontend.login

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
import androidx.compose.runtime.Composable
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
import com.edu.auri.backend.registration.AuthViewModel
import com.edu.auri.frontend.components.EmailField
import com.edu.auri.frontend.components.PasswordField
import com.edu.auri.ui.theme.AuriTheme

@Composable
fun LoginScreen (modifier: Modifier = Modifier, navController: NavController,
                 authViewModel: AuthViewModel) {
    Surface (
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column (
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
                    modifier = Modifier
                        .fillMaxWidth()
                )
                    Spacer(modifier = Modifier.height(28.dp))
                    EmailField()
                    Spacer(modifier = Modifier.height(28.dp))
                    PasswordField()
                    Spacer(modifier = Modifier.height(28.dp))

                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Login")
                }


            }

        }
    }
}

@Composable
@Preview
fun LoginScreenPreview () {
    AuriTheme { LoginScreen(Modifier, NavController(LocalContext.current), AuthViewModel()) }
}